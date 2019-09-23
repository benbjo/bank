package no.benjamin.bank.web.resources;


import no.benjamin.bank.dto.ForsikringsAvtaleDto;
import no.benjamin.bank.dto.ForsikringsavtaleResultatDto;
import no.benjamin.bank.service.AvtaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/avtale")
@RestController
public class ForsikringsAvtaleResource {
    private final AvtaleService avtaleService;

    @Autowired
    public ForsikringsAvtaleResource(AvtaleService avtaleService){
        this.avtaleService = avtaleService;
    }

    @PostMapping
    public ForsikringsavtaleResultatDto opprettAvtale(@RequestBody ForsikringsAvtaleDto forsikringsAvtale) {
        return avtaleService.opprettAvtale(forsikringsAvtale);
    }
}
