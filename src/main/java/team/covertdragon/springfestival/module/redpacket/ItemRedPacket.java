/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.redpacket;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.apache.commons.lang3.StringUtils;
import team.covertdragon.springfestival.internal.capabilities.ItemStackInventoryProvider;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRedPacket extends Item {

    ItemRedPacket(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound data = stack.getTag();
        if (data != null) {
            tooltip.add(new TextComponentTranslation("redpacket.owner", data.getString("owner_name")));
            // TODO (3TUSK): cascading text component
            tooltip.add(new TextComponentTranslation("redpacket.receiver", StringUtils.defaultIfEmpty(data.getString("receiver_name"), I18n.format("redpacket.receiver.any"))));
            tooltip.add(new TextComponentTranslation("redpacket.message", StringUtils.defaultIfEmpty(data.getString("message"), I18n.format("redpacket.message.empty"))));
            tooltip.add(new TextComponentTranslation(data.getBoolean("passcode") ? "redpacket.mode.passcode" : "redpacket.mode.regular"));
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ItemStackInventoryProvider(8);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        if (!worldIn.isRemote && handIn == EnumHand.MAIN_HAND) { // Somehow realistic, more for simplicity
            ItemStack s = player.getHeldItemMainhand();
            if (s.getItem() == this) { // TODO (3TUSK): respect registry override, we need to put a static field somewhere else
                NBTTagCompound data = s.getTag();
                if (data == null) {
                    data = new NBTTagCompound();
                    s.setTag(data);
                }
                if (data.contains("owner", data.getId())) {
                    if (!NBTUtil.readUniqueId(data.getCompound("owner")).equals(player.getUniqueID())) {
                        return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(handIn));
                    }
                } else {
                    data.put("owner", NBTUtil.writeUniqueId(player.getUniqueID()));
                    data.putString("owner_name", player.getName().getString());
                }
                // TODO (3TUSK): Fix GUI
                // player.openGui(SpringFestival.getInstance(), 0, worldIn, (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }
}
