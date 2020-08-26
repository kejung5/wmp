package com.example.demo.service;

import com.example.demo.dto.ResultRequest;
import com.example.demo.dto.ResultResponse;
import com.example.demo.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@Slf4j
public class WebService {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    public ResultResponse getResult(ResultRequest request) throws Exception {
        ResultResponse response = ResultResponse.builder().build();

        try {
            ResponseEntity<String> res = restTemplate.getForEntity(request.getUrl(), String.class);
            log.info("### [getResult] response : {} ###", res);

            if (res.getStatusCode() == HttpStatus.OK) {
                String body = res.getBody();

                // 태그지우기
                if ("except".equals(request.getType())) {
                    body = StringUtil.removeTag(body);
                }
                log.debug("### [getResult] removeTag : {} ###", body);

                // 문자추출
                body = StringUtil.getNumberWithEnglish(body);
                String number = StringUtil.getNumber(body);
                String english = StringUtil.getEnglish(body);
                log.debug("### [getResult] number : {} ###", number);
                log.debug("### [getResult] english : {} ###", english);

                // 정렬
                String[] arrNumber = number.split("");
                Arrays.sort(arrNumber);
                log.debug("### [getResult] arrNumber : {} ###", String.join("", arrNumber));

                english = getSortEnglish(english);
                String[] arrEnglish = english.split("");

                int cntNumber = number.length();
                int cntEnglish = english.length();

                int max = cntNumber;
                if (max < cntEnglish) {
                    max = cntEnglish;
                }

                // 교차출력
                String strResult = "";
                for (int i=0; i < max; i++) {
                    if (i < cntEnglish) {
                        strResult = strResult.concat(arrEnglish[i]);
                    }
                    if (i < cntNumber) {
                        strResult = strResult.concat(arrNumber[i]);
                    }
                }
                log.debug("### [getResult] strResult : {} ###", strResult);

                // 출력묶음단위 계산
                int unit = request.getUnit();
                int cntResult = strResult.length();
                int share = Math.floorDiv(cntResult, unit);
                int rest = Math.floorMod(cntResult, unit);

                response.setShare(share);
                response.setRest(rest);
            }

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getSortEnglish(String inputStr) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append(inputStr.charAt(0));

        for (int i = 1 ; i < inputStr.length(); i++) {
            char orgChar = inputStr.charAt(i);
            char orgCmpChar = Character.isLowerCase(orgChar) ? Character.toUpperCase(orgChar) : orgChar;

            for(int j = 0; j < sb.length(); j++) {
                char cmpChar = sb.charAt(j);
                cmpChar = Character.toUpperCase(cmpChar);

                if(orgCmpChar == cmpChar) {
                    if(Character.isUpperCase(orgChar)) {
                        sb.insert(j, orgChar);
                        break;
                    } else {
                        if(j >= sb.length() -1) {
                            sb.append(orgChar);
                            break;
                        }
                    }
                } else if( orgCmpChar >= cmpChar) {
                    if(j >= sb.length() -1) {
                        sb.append(orgChar);
                        break;
                    }
                } else {
                    sb.insert(j, orgChar);
                    break;
                }
            }
        }
        return sb.toString();
    }


}
