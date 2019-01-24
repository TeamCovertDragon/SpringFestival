/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.internal.recipes.conditions;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import team.covertdragon.springfestival.SpringFestival;

import java.util.function.BooleanSupplier;

public class ConditionFactorySpringFestivalModule implements IConditionFactory {
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        try {
            final String moduleID = JsonUtils.getString(json, "module");
            return () -> SpringFestival.proxy.isModuleLoaded(moduleID);
        } catch (Exception e) {
            throw new JsonSyntaxException("Failed to parse Spring Festival module condition", e);
        }
    }
}
