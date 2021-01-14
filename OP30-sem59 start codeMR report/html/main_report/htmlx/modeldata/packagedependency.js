var matrix = [[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,1,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],[0,0,2,0,1,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]]
var packages = [{
"name": " nl.tudelft.unischeduler.viewer.entities", "color": " #3182bd"
}
,{
"name": " nl.tudelft.unischeduler.viewer.controllers", "color": " #6baed6"
}
,{
"name": " nl.tudelft.unischeduler.rules.core", "color": " #9ecae1"
}
,{
"name": " nl.tudelft.unischeduler.database.exception", "color": " #c6dbef"
}
,{
"name": " nl.tudelft.unischeduler.rules.services", "color": " #e6550d"
}
,{
"name": " nl.tudelft.unischeduler.database", "color": " #fd8d3c"
}
,{
"name": " nl.tudelft.unischeduler.database.lecture", "color": " #fdae6b"
}
,{
"name": " nl.tudelft.unischeduler.schedulegenerate.generator", "color": " #fdd0a2"
}
,{
"name": " nl.tudelft.unischeduler.database.classroom", "color": " #31a354"
}
,{
"name": " nl.tudelft.unischeduler.schedulegenerate", "color": " #74c476"
}
,{
"name": " nl.tudelft.unischeduler.rules", "color": " #a1d99b"
}
,{
"name": " nl.tudelft.unischeduler.discoveryserver", "color": " #c7e9c0"
}
,{
"name": " nl.tudelft.unischeduler", "color": " #756bb1"
}
,{
"name": " nl.tudelft.unischeduler.schedulegenerate.entities", "color": " #9e9ac8"
}
,{
"name": " nl.tudelft.unischeduler.viewer.services", "color": " #bcbddc"
}
,{
"name": " nl.tudelft.unischeduler.database.triggers", "color": " #dadaeb"
}
,{
"name": " nl.tudelft.unischeduler.rules.storing", "color": " #636363"
}
,{
"name": " nl.tudelft.unischeduler.sysinteract", "color": " #969696"
}
,{
"name": " nl.tudelft.unischeduler.scheduleedit.controller", "color": " #bdbdbd"
}
,{
"name": " nl.tudelft.unischeduler.database.sicklog", "color": " #d9d9d9"
}
,{
"name": " nl.tudelft.unischeduler.utilentities", "color": " #3182bd"
}
,{
"name": " nl.tudelft.unischeduler.user", "color": " #6baed6"
}
,{
"name": " nl.tudelft.unischeduler.database.usercourse", "color": " #9ecae1"
}
,{
"name": " nl.tudelft.unischeduler.authentication", "color": " #c6dbef"
}
,{
"name": " nl.tudelft.unischeduler.database.course", "color": " #e6550d"
}
,{
"name": " nl.tudelft.unischeduler.database.lectureschedule", "color": " #fd8d3c"
}
,{
"name": " nl.tudelft.unischeduler.scheduleedit.core", "color": " #fdae6b"
}
,{
"name": " nl.tudelft.unischeduler.rules.entities", "color": " #fdd0a2"
}
,{
"name": " nl.tudelft.unischeduler.database.user", "color": " #31a354"
}
,{
"name": " nl.tudelft.unischeduler.database.schedule", "color": " #74c476"
}
,{
"name": " nl.tudelft.unischeduler.scheduleedit", "color": " #a1d99b"
}
,{
"name": " nl.tudelft.unischeduler.viewer", "color": " #c7e9c0"
}
,{
"name": " nl.tudelft.unischeduler.scheduleedit.services", "color": " #756bb1"
}
,{
"name": " nl.tudelft.unischeduler.scheduleedit.exception", "color": " #9e9ac8"
}
,{
"name": " nl.tudelft.unischeduler.rules.controllers", "color": " #bcbddc"
}
,{
"name": " nl.tudelft.unischeduler.schedulegenerate.api", "color": " #dadaeb"
}
];
