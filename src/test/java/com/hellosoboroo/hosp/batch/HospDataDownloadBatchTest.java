package com.hellosoboroo.hosp.batch;

import com.hellosoboroo.hosp.domain.Hospital;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : com.hellosoboroo.hosp.batch
 * fileName       : HospDataDownloadBatchTest
 * author         : admin
 * date           : 2022-02-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-02-24        admin       최초 생성
 */
class HospDataDownloadBatchTest {

    @Test
    public void start(){
        //1. 공공데이터 다운로드
        RestTemplate rt = new RestTemplate();
        //serviceKey 인코딩 문제로 URI 객체로 감싸야한다
        String url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=AAdyQrnUawyJF9kahQ0dtEmGaYu4LRlURww94x%2BM2T4EHezuTAjhsSblm1oguxPGm8GIaE%2F5rKryzWWNChGi2Q%3D%3D&pageNo=1&numOfRows=10&_type=json";
        URI uri = null;
        try{
            uri = new URI(url);
            ResponseDto dto = rt.getForObject(uri,ResponseDto.class);
            //System.out.println(dto);
            List<Item> hospitals = dto.getResponse().getBody().getItems().getItem();
            for(Item item : hospitals){
                System.out.println(item.getYadmNm());
                System.out.println("PCR 여부 : " + item.getPcrPsblYn());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @DisplayName("공공 데이터 다운로드 테스트 컬력센 담기 (전체 데이터)")
    @Test
    public void download(){

        //1. 담을 그릇 준비
        List<Hospital> hospitals = new ArrayList<>();

        //2. api 한번 호출해서 totalcount 확인

        RestTemplate rt = new RestTemplate();
        //serviceKey 인코딩 문제로 URI 객체로 감싸야한다
        //사이즈를 1로 했더니 item이 컬렉션이 아니라서 파싱이 안되서 2로 바꿈
        int totalCount = 2;
        String url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=AAdyQrnUawyJF9kahQ0dtEmGaYu4LRlURww94x%2BM2T4EHezuTAjhsSblm1oguxPGm8GIaE%2F5rKryzWWNChGi2Q%3D%3D&pageNo=1&numOfRows="+totalCount+"&_type=json";
        URI uri = null;

        try{
            uri = new URI(url);
            ResponseDto totalCountDto = rt.getForObject(uri,ResponseDto.class);
            totalCount = totalCountDto.getResponse().getBody().getTotalCount();

            url = "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=AAdyQrnUawyJF9kahQ0dtEmGaYu4LRlURww94x%2BM2T4EHezuTAjhsSblm1oguxPGm8GIaE%2F5rKryzWWNChGi2Q%3D%3D&pageNo=1&numOfRows="+totalCount+"&_type=json";
            uri = new URI(url);

            //3. totalCount 만큼 한번에 가져오기 totalCount = 5136
            ResponseDto responseDto = rt.getForObject(uri,ResponseDto.class);

            List<Item> items = responseDto.getResponse().getBody().getItems().getItem();
            System.out.println("가져온 데이터 사이즈 : "+ items.size());

            hospitals = items.stream()
                    .map(item -> Hospital.builder()
                            .addr(item.getAddr())
                            .mgtStaDd(item.getMgtStaDd())
                            .pcrPsblYn(item.getPcrPsblYn())
                            .ratPsblYn(item.getRatPsblYn())
                            .recuClCd(item.getRecuClCd())
                            .rnum(item.getRnum())
                            .rprtWorpClicFndtTgtYn(item.getRprtWorpClicFndtTgtYn())
                            .sgguCdNm(item.getSgguCdNm())
                            .sidoCdNm(item.getSidoCdNm())
                            .telno(item.getTelno())
                            .xPosWgs84(item.getXPosWgs84())
                            .yPosWgs84(item.getYPosWgs84())
                            .yadmNm(item.getYadmNm())
                            .ykihoEnc(item.getYkihoEnc())
                            .xPos(item.getXPos())
                            .yPos(item.getYPos())
                            .build()

                    )
                    .collect(Collectors.toList());


            assertEquals(totalCount,items.size());


//            for(Item item : items){
//                System.out.println(item.getYadmNm());
//                System.out.println("PCR 여부 : " + item.getPcrPsblYn());
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}