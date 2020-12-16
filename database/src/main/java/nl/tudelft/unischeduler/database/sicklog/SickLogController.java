package nl.tudelft.unischeduler.database.sicklog;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SickLogController {

    @Autowired
    private transient SickLogService sickLogService;

    @GetMapping(path = "/sickLogs/all")
    public @ResponseBody
    List<SickLog> getAllSickLogs() {
        return sickLogService.getAllSickLogs();
    }

    @PutMapping(path = "/sickLogs/new/{netId}/{reportSick}")
    public @ResponseBody
    ResponseEntity<?> setUserSick(@PathVariable String netId, @PathVariable Timestamp reportSick) {
        return sickLogService.setUserSick(netId, reportSick);
    }

}
