package com.backend.ayBank.controllers;


import com.backend.ayBank.requests.CreditRequest;
import com.backend.ayBank.responses.CreditResponse;
import com.backend.ayBank.responses.UserResponse;
import com.backend.ayBank.services.CreditService;
import com.backend.ayBank.shared.dto.CreditDto;
import com.backend.ayBank.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/credit")
@CrossOrigin(origins = "*")

public class CreditController {
    @Autowired
    CreditService creditService;

    // database ( email/N compte + annuity/capital/duration/rate + state(accepted/waiting/refused)
    @GetMapping
    public ResponseEntity<List<CreditResponse>> getALlCredit(Principal principal){
        List<CreditDto> creditDtoList = creditService.getAllCredit(principal.getName());
        Type listType = new TypeToken<List<CreditResponse>>(){}.getType();
        List<CreditResponse> listCredits = ModelMapperUtil.modelMapper().map(creditDtoList,listType);
        return new ResponseEntity<>(listCredits, HttpStatus.OK);
    }

    @PutMapping
    public CreditResponse updateCredit(String idCredit, @RequestBody CreditDto creditDto){
        return null;
    }
    @PostMapping
    public ResponseEntity<CreditResponse> postCredit(@RequestBody CreditRequest creditRequest,Principal principal){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CreditDto creditDto = modelMapper.map(creditRequest, CreditDto.class);
//        BeanUtils.copyProperties(creditRequest, creditDto);

        CreditDto credit = creditService.createCredit(creditDto, principal.getName());

        CreditResponse creditResponse =modelMapper.map(credit,CreditResponse.class);
//        BeanUtils.copyProperties(credit, creditResponse);

        return new ResponseEntity<>(creditResponse,HttpStatus.CREATED);
    }
//    @GetMapping
//    public UserResponse putCredit(){
//        return null;
//    }
//    @GetMapping
//    public UserResponse deleteCredit(){
//        return null;
//    }

    @GetMapping("/annuityP/{capital}/{rate}/{duration}")
    public double processAnnuity(
            @PathVariable(name ="capital") double capital,
            @PathVariable(name ="rate") double rate,
            @PathVariable(name ="duration") long duration){
        double rateFinal=annuelle(rate);
//         return String.valueOf((Math.pow(1 + rateFinal, duration)*rateFinal*capital)/(Math.pow(1+rateFinal, duration)-1));
        return (Math.pow(1 + rateFinal, duration)*rateFinal*capital)/(Math.pow(1+rateFinal, duration)-1);


    }

    @GetMapping("/capitalP/{annuity}/{rate}/{duration}")
    public double processCapital(
            @PathVariable(name ="capital") double annuity,
            @PathVariable(name ="rate") double rate,
            @PathVariable(name ="duration") long duration){
        double rateF=annuelle(rate);
   return (Math.pow(1+rateF, duration)*annuity -annuity)/(Math.pow(1+rateF, duration)*rateF);




    }


    @GetMapping("/durationP/{annuity}/{capital}/{rate}")
    public double processduration(
            @PathVariable(name ="annuity") long annuity,
            @PathVariable(name ="capital") double capital,
            @PathVariable(name ="rate") double rate
          ){
        double rateF=annuelle(rate);
     return  (long) (((Math.log(annuity/(annuity-rateF*capital)))/(Math.log(1+rateF))) +0.5);

    }







    public double annuelle(Double rate) {
        return Math.pow(1 + rate,(double)1/12)-1;
    }




}
