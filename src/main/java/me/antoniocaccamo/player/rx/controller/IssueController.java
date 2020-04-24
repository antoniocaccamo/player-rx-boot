package me.antoniocaccamo.player.rx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author antoniocaccamo on 21/04/2020
 */

@RestController("/issues")
public class IssueController {

    @GetMapping("/{number}")
    public String issue(@PathVariable Integer number) {
        return "Issue # " + number + "!";
    }
}
