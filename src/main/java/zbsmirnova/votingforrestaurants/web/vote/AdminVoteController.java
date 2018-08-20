package zbsmirnova.votingforrestaurants.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.service.VoteService;
import zbsmirnova.votingforrestaurants.web.user.AdminController;

import java.util.List;

@RestController
@RequestMapping(AdminVoteController.URL)
public class AdminVoteController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    VoteService service;

    //URL = "/admin/restaurants/votes"
    static final String URL = "/admin/votes";

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete vote {}", id);
        service.delete(id);
    }

    @GetMapping
    public List<Vote> getAll(){
        log.info("get all votes");
        return service.getAll();
    }

    @GetMapping(value = "/{restaurantId}")
    public List<Vote> getAll(@PathVariable("restaurantId") int restaurantId){
        log.info("get all votes for restaurant {}", restaurantId);
        return service.getAll(restaurantId);
    }



}
