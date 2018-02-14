package team.covertdragon.springfestival.module.fortune.fortunevaluesystem;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.CapabilityLoader;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.capability.IFortuneValueSystem;
import team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines.AbstractTileFVMachine;

import java.util.List;

public class FortuneValueManager implements Runnable {
    public boolean alive = false;
    private MinecraftServer server;
    private List<EntityPlayerMP> playerList;

    public FortuneValueManager(MinecraftServer server) {
        this.server = server;
    }

    public void updatePlayerList() {
        playerList = server.getPlayerList().getPlayers();
    }

    @Override
    public void run() {
        SpringFestivalConstants.logger.info("Starting fortune value handler...");
        while (alive) {
            for (EntityPlayerMP player : playerList) {
                if (player != null)
                    updatePlayerFortuneValue(player);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                SpringFestivalConstants.logger.catching(e);
            }
        }
    }

    private void updatePlayerFortuneValue(EntityPlayerMP player) {
        IFortuneValueSystem system = player.getCapability(CapabilityLoader.fortuneValue, null);
        if (system != null) {
            //Update player fortune level
            system.addFortune(system.getIncreasingPoint());

            //Tick FV machines
            for (AbstractTileFVMachine machine : system.getFVMachines()) {
                if (machine != null && machine.getWorld().getTileEntity(machine.getPos()) != null && system.shrinkFortune(machine.getRequiredFV())) {
                    machine.onFVProvided();
                }
            }
        } else {
            throw new RuntimeException("Unable to read fv system info for player " + player.getGameProfile().getName());
        }
    }

    private void cleanMachineList(EntityPlayerMP player) {
        IFortuneValueSystem system = player.getCapability(CapabilityLoader.fortuneValue, null);
        if (system != null) {
            for (AbstractTileFVMachine machine : system.getFVMachines()) {
                if (machine != null && machine.getWorld().getTileEntity(machine.getPos()) == null) {
                    system.deleteMachine(machine.getId());
                }
            }

            for (int i = 0; i < system.getFVMachines().size(); i++) {
                if (system.getFVMachines().get(i) == null) {
                    system.getFVMachines().remove(i);
                    i--;
                }
            }
        } else {
            throw new RuntimeException("Unable to read fv system info for player " + player.getGameProfile().getName());
        }
    }
}
