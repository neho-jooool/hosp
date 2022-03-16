package com.hellosoboroo.hosp.web;

import com.hellosoboroo.hosp.domain.Hospital;
import com.hellosoboroo.hosp.domain.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * packageName    : com.hellosoboroo.hosp.web
 * fileName       : HospitalController
 * author         : admin
 * date           : 2022-03-03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-03-03        admin       최초 생성
 */
@RequiredArgsConstructor
@Controller
public class HospitalController {
    private final HospitalRepository hospitalRepository;

    @GetMapping("/")
    public String index(Model model){

        List<String> sidoCdNms = hospitalRepository.mFindSidoCdNm();
        String sidoCdNm = sidoCdNms.size() > 0 ? sidoCdNms.get(0) : "";

        model.addAttribute("sidoCdNms",sidoCdNms);
        model.addAttribute("sgguCdNms",hospitalRepository.mFindSggucdnm(sidoCdNm));

        return "index"; //templates/home.mustache 찾음
    }

    @GetMapping("/api/sggucdnm")
    //응답도 json으로 할 예정
    public @ResponseBody List<String> sggucdnm(String sidoCdNm){// 버퍼로 ajax 요청 받음
        return hospitalRepository.mFindSggucdnm(sidoCdNm);
    }

    @GetMapping("/api/hospital")
    public @ResponseBody List<Hospital> hospitals(String sidoCdNm, String sgguCdNm){

        return hospitalRepository.mFindHospital(sidoCdNm,sgguCdNm);
    }
}
