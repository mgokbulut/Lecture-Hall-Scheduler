package nl.tudelft.unischeduler.database.SickLog;

import nl.tudelft.unischeduler.database.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SickLogController {

    @Autowired
    private transient SickLogService sickLogService;

    @GetMapping(path = "/sickLogs/all")
    public @ResponseBody
    List<SickLog> getAllSickLogs(){
        return sickLogService.getAllSickLogs();
    }

}
