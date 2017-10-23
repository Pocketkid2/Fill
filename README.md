# Fill
A Bukkit/Spigot plugin for Minecraft servers used for filling container blocks very rapidly.

Use `mvn install` to build, or select `maven install` from eclipse `Run As` menu

Usage is simple, just hold an item in your hand you want to fill with and type `/fill`. This creates a "fill wand".
The fill want will fill every slot in a container you right click (chest, dispenser, etc) with that item, including any
data and the quantity. You can obtain a wand with specific data by typing `/fill <item-id> <quantity> <data>`. 

NOTE: The wand only works in default worlds, you need to edit the config for other or specific worlds!

The fill action has a sound and message to confirm that it happened. These can be disabled in the config.

The command permission, `fill.command` is by default given to OPs. 
