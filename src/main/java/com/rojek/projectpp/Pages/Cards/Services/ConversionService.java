package com.rojek.projectpp.Pages.Cards.Services;

import com.rojek.projectpp.Pages.Cards.Models.Currency;
import com.rojek.projectpp.Pages.Cards.Repositories.ConversionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConversionService {

    private final ConversionRepository conversionRepository;

    public double getWorth(Currency from, Currency to) {
        return conversionRepository.getWorth(from, to);
    }

}
