package io.cloudsystem.addon;

import io.cloudsystem.signs.events.SignUpdateEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.material.Sign;

public class SignUpdateListener implements Listener {

    @EventHandler
    public void onUpdate(SignUpdateEvent e) {

        Block block = e.getSign().getBlock();

        //Break for invalid blocks
        if (!block.getType().equals(Material.WALL_SIGN) && e.getSignObject().getServerObject() != null && e.getSignObject().getServerObject().getStatus() != null) return;

        //Get block behind sign
        Sign sign = (Sign) block.getState().getData();
        Block attached = block.getRelative(sign.getAttachedFace());
        attached.setType(Material.STAINED_CLAY);

        //Set color for status
        switch (e.getSignObject().getServerObject().getStatus()) {

            case "Waiting":
                attached.setData((byte) 5);
                break;

            case "Restart":
                attached.setData((byte) 4);
                break;

            case "Offline":
                attached.setData((byte) 14);
                break;

        }

    }

}
