package com.hellosoboroo.hosp.batch;

import lombok.Data;

import java.util.List;

/**
 * packageName    : com.hellosoboroo.hosp.batch
 * fileName       : ResponseDto
 * author         : admin
 * date           : 2022-02-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-02-24        admin       최초 생성
 */
@Data
class Body {

    private Items items;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;

}
@Data
class Header {

    private String resultCode;
    private String resultMsg;

}
@Data
class Item {

    private String addr;
    private Integer mgtStaDd;
    private String pcrPsblYn;
    private String ratPsblYn;
    private Integer recuClCd;
    private Integer rnum;
    private String rprtWorpClicFndtTgtYn;
    private String sgguCdNm;
    private String sidoCdNm;
    private String telno;
    private Double xPosWgs84;
    private String yPosWgs84;
    private String yadmNm;
    private String ykihoEnc;
    private Integer xPos;
    private Integer yPos;

}
@Data
class Items {
    private List<Item> item = null;
}

@Data
class Response {

    private Header header;
    private Body body;

}
@Data
public class ResponseDto {

    private Response response;

}