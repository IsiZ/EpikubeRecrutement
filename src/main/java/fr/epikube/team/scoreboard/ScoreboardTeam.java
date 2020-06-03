package fr.epikube.team.scoreboard;

import fr.epikube.team.roles.Roles;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

import java.lang.reflect.Field;
import java.util.List;

public class ScoreboardTeam {

    private Roles role;

    public ScoreboardTeam(Roles rank) {
       this.role = rank;
    }

    private PacketPlayOutScoreboardTeam createPacket(int mode){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

        // a : team name

        // h : mode
        /**
         If 0 then the team is created.
         If 1 then the team is removed.
         If 2 the team team information is updated.
         If 3 then new players are added to the team.
         If 4 then players are removed from the team.
         */

        // b : display name
        // c : prefix
        // d : suffix
        // i : friendly fire (0 off, 1 on)
        // e : name tag visibility
        // f : chat color

        setField(packet, "a", Integer.toString(role.getId()));
        setField(packet, "h", mode);
        setField(packet, "b", "");

        // If the the role prefix length is more than 10 I substring else I put the prefix without substring
        String name = role.getPrefix().length() > 10 ? role.getPrefix().substring(0, 11) : role.getPrefix();
        setField(packet, "c", role.getBukkitColor() + name + " ");
        setField(packet, "d", "");
        setField(packet, "i", 0);
        setField(packet, "e", "always");
        setField(packet, "f", 0);

        return packet;
    }

    public PacketPlayOutScoreboardTeam createTeam(){
        return createPacket(0);
    }

    public PacketPlayOutScoreboardTeam updateTeam(){
        return createPacket(2);
    }

    public PacketPlayOutScoreboardTeam removeTeam(){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", Integer.toString(role.getId()));
        setField(packet, "h", 1);

        return packet;
    }

    public PacketPlayOutScoreboardTeam setFriendlyFire(boolean v){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "i", (v ? 1 : 0));

        return packet;
    }

    public PacketPlayOutScoreboardTeam addOrRemovePlayer(int mode, String playerName){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", Integer.toString(role.getId()));
        setField(packet, "h", mode);

        try {
            Field f = packet.getClass().getDeclaredField("g");
            f.setAccessible(true);
            ((List<String>) f.get(packet)).add(playerName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static PacketPlayOutPlayerInfo updateDisplayName(EntityPlayer player) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, player);
        player.playerConnection.sendPacket(packet);
        return packet;
    }

    private void setField(Object edit, String fieldName, Object value) {
        try {
            Field field = edit.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(edit, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Roles getRole() {
        return role;
    }
}
