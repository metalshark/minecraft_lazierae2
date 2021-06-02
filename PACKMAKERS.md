# **Packmaker Guide**

This page shows everything you need to know about adjusting the mod with the help of [CraftTweaker] or [KubeJS].

For all recipes:<br>
If the processing time is not provided, it will use the default value from the mod's configuration.

Advice:<br>
It's not recommended to have recipes with the same input for two different slots.<br>
If you have the same input in multiple recipes, you should set them to the same input slot.

In case you add any recipe for a machine with more than one input slot, you sometimes need to restart the world
after the reload (go back to main menu and rejoin).

## CraftTweaker
The mod has native support for it, so there are some functions you can use.

**Fluix Aggregator**
```zs
// add a recipe
<recipetype:lazierae2:fluix_aggregator>.addRecipe(String name, ItemStack output, Optional<Integer> processingTime in ticks, [Ingredient input1, Ingredient input2, Ingredient input3]);
// remove a recipe
<recipetype:lazierae2:fluix_aggregator>.removeRecipe(ItemStack output);

// example to get 3 dirt from stone, apple and potato in 200 ticks (10 seconds)
<recipetype:lazierae2:fluix_aggregator>.addRecipe("example_recipe", <item:minecraft:dirt> * 3, 200, [<item:minecraft:stone>, <item:minecraft:apple>, <item:minecraft:potato>]);
```

**Pulse Centrifuge**
```zs
// add a recipe
<recipetype:lazierae2:pulse_centrifuge>.addRecipe(String name, ItemStack output, Optional<Integer> processingTime in ticks, Ingredient input);
// remove a recipe
<recipetype:lazierae2:pulse_centrifuge>.removeRecipe(ItemStack output);

// example to get 3 dirt from stone
<recipetype:lazierae2:pulse_centrifuge>.addRecipe("example_recipe", <item:minecraft:dirt> * 3, <item:minecraft:stone>);
```

**Crystal Energizer**
```zs
// add a recipe
<recipetype:lazierae2:crystal_energizer>.addRecipe(String name, ItemStack output, Optional<Integer> processingTime in ticks, Ingredient input);
// remove a recipe
<recipetype:lazierae2:crystal_energizer>.removeRecipe(ItemStack output);

// example to get 2 ender pearls from apple in 60 ticks (3 seconds)
<recipetype:lazierae2:crystal_energizer>.addRecipe("example_recipe", <item:minecraft:ender_pearl> * 2, 60, <item:minecraft:apple>);
```

**Circuit Etcher**
```zs
// add a recipe
<recipetype:lazierae2:circuit_etcher>.addRecipe(String name, ItemStack output, Optional<Integer> processingTime in ticks, [Ingredient input1, Ingredient input2, Ingredient input3]);
// remove a recipe
<recipetype:lazierae2:circuit_etcher>.removeRecipe(ItemStack output);

// example to get 3 dirt from stone, apple and potato in 200 ticks (10 seconds)
<recipetype:lazierae2:circuit_etcher>.addRecipe("example_recipe", <item:minecraft:dirt> * 3, 200, [<item:minecraft:stone>, <item:minecraft:apple>, <item:minecraft:potato>]);
```

## KubeJS
This mod has no custom plugin for [KubeJS] but you can always use the `event.custom()` function inside the recipe event to add new recipes.<br>
Either you always pass in the whole recipe object or you write yourself a small wrapper for that.

Existing recipes you can adapt the style from are [here][recipes].

**Fluix Aggregator**
```js
event.custom({
    type: 'lazierae2:fluix_aggregator', // always needed
    processing_time: 150, // processing time in ticks as Integer, optional
    output: {
        item: 'lazierae2:carb_fluix_dust' // output as ItemStack
    },
    inputs: [
        {
            input: {
                tag: 'forge:dusts/coal' // input 1 as Ingredient
            }
        },
        {
            input: {
                item: 'appliedenergistics2:fluix_dust' // input 2 as Ingredient
            }
        },
        {
            input: {
                item: 'appliedenergistics2:silicon' // input 3 as Ingredient
            }
        }
    ]
});
```

**Pulse Centrifuge**
```js
event.custom({
    type: 'lazierae2:pulse_centrifuge', // always needed
    processing_time: 200, // processing time in ticks as Integer, optional
    output: {
        item: 'appliedenergistics2:ender_dust' // output as ItemStack
    },
    input: {
        tag: 'forge:ender_pearls' // input as Ingredient
    }
});
```

**Crystal Energizer**
```js
event.custom({
    type: 'lazierae2:crystal_energizer', // always needed
    processing_time: 180, // processing time in ticks as Integer, optional
    output: {
        item: 'appliedenergistics2:charged_certus_quartz_crystal' // output as ItemStack
    },
    input: {
        item: 'appliedenergistics2:certus_quartz_crystal' // input as Ingredient
    }
});
```

**Circuit Etcher**
```js
event.custom({
    type: 'lazierae2:circuit_etcher', // always needed
    processing_time: 150, // processing time in ticks as Integer, optional
    output: {
        item: 'appliedenergistics2:logic_processor' // output as ItemStack
    },
    inputs: [
        {
            input: {
                tag: 'forge:ingots/gold' // input 1 as Ingredient
            }
        },
        {
            input: {
                tag: 'forge:dusts/redstone' // input 2 as Ingredient
            }
        },
        {
            input: {
                item: 'appliedenergistics2:silicon' // input 3 as Ingredient
            }
        }
    ]
});
```

<!-- Links -->
[crafttweaker]: https://www.curseforge.com/minecraft/mc-mods/crafttweaker
[kubejs]: https://www.curseforge.com/minecraft/mc-mods/kubejs-forge
[recipes]: https://github.com/RLNT/minecraft_lazierae2/blob-1.16/src/generated/resources/data/lazierae2/recipes
