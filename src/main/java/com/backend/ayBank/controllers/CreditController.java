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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/credit")
@CrossOrigin(origins = "*")

public class CreditController {
    @Autowired
    CreditService creditService;
//    String[] typeCredits = [
//            '',
//            'Financial institution loans',
//            'Agricultural loans',
//            'Commercial and industrial loans',
//            'Loans to individuals'
//            ];

    Map<String,Double> valueOfType = new HashMap<>();

    // database ( email/N compte + annuity/capital/duration/typeCredit + state(accepted/waiting/refused)
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
    @GetMapping("/test")
    public String testCredit(){
        return "test";
    }
    @PostMapping("/test")
    public  ResponseEntity<String> testpostCredit(){
        return new ResponseEntity<String>(new String("youssef") ,HttpStatus.CREATED);
    }
//    @GetMapping
//    public UserResponse deleteCredit(){
//        return null;
//    }


    @GetMapping("/annuityP/{capital}/{typeCredit}/{duration}")
    public double processAnnuity(
            @PathVariable(name ="capital") double capital,
            @PathVariable(name ="typeCredit") String typeCredit,
            @PathVariable(name ="duration") long duration){
        System.out.println(annuelle(typeCredit));
        double typeCreditFinal=annuelle(typeCredit);
//         return String.valueOf((Math.pow(1 + typeCreditFinal, duration)*typeCreditFinal*capital)/(Math.pow(1+typeCreditFinal, duration)-1));
        return (Math.pow(1 + typeCreditFinal, duration)*typeCreditFinal*capital)/(Math.pow(1+typeCreditFinal, duration)-1);


    }

    @GetMapping("/capitalP/{annuity}/{typeCredit}/{duration}")
    public double processCapital(
            @PathVariable(name ="capital") double annuity,
            @PathVariable(name ="typeCredit") String typeCredit,
            @PathVariable(name ="duration") long duration){
        double typeCreditF=annuelle(typeCredit);
   return (Math.pow(1+typeCreditF, duration)*annuity -annuity)/(Math.pow(1+typeCreditF, duration)*typeCreditF);




    }


    @GetMapping("/durationP/{annuity}/{capital}/{typeCredit}")
    public double processduration(
            @PathVariable(name ="annuity") long annuity,
            @PathVariable(name ="capital") double capital,
            @PathVariable(name ="typeCredit") String typeCredit
          ){
        double typeCreditF=annuelle(typeCredit);
     return  (long) (((Math.log(annuity/(annuity-typeCreditF*capital)))/(Math.log(1+typeCreditF))) +0.5);

    }







    public double annuelle(String typeCredit) {
        valueOfType .put("Financialinstitutionloans",0.5);
        valueOfType .put("Agriculturalloans",0.45);
        valueOfType.put("Commercialandindustrialloans",0.6);
        valueOfType.put("Loanstoindividuals",0.5);
        valueOfType.put("Realestateloans",0.7);

        return Math.pow(1 + valueOfType.get(typeCredit),(double)1/12)-1;
    }




}
