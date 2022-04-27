package com.rojek.projectpp.Pages.History;

import com.rojek.projectpp.Pages.Transfer.Models.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Filter {

    private String phrase;
    private List<Status> statuses;

    public Filter() {
        this.statuses = new ArrayList<>();
    }

    public boolean isEmpty() {
        if((phrase==null|| phrase=="") && (statuses.isEmpty()))
            return true;
        else return false;
    }
}
