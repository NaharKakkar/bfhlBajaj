package com.example.bfhl.service.impl;

import com.example.bfhl.dto.RequestDto;
import com.example.bfhl.dto.ResponseDto;
import com.example.bfhl.service.BfhlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    // static user details - adjust if needed
    private static final String FULL_NAME = "john doe"; // must be lowercase per spec
    private static final String DOB_DDMMYYYY = "17091999";
    private static final String EMAIL = "john@xyz.com";
    private static final String ROLL = "ABCD123";

    @Override
    public ResponseDto process(RequestDto request) {
        ResponseDto resp = new ResponseDto();
        try {
            List<String> data = request.getData();
            if (data == null) data = Collections.emptyList();

            List<String> odd = new ArrayList<>();
            List<String> even = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specials = new ArrayList<>();
            long sum = 0L;
            List<Character> allAlphaChars = new ArrayList<>();

            for (String token : data) {
                if (token == null) continue;
                String t = token.trim();
                if (t.matches("^\\d+$")) {
                    // numeric
                    try {
                        long val = Long.parseLong(t);
                        sum += val;
                        if ((val % 2) == 0) {
                            even.add(t);
                        } else {
                            odd.add(t);
                        }
                    } catch (NumberFormatException e) {
                        // treat as special if too large
                        specials.add(t);
                    }
                } else if (t.matches("^[A-Za-z]+$")) {
                    alphabets.add(t.toUpperCase());
                    for (char c : t.toCharArray()) {
                        allAlphaChars.add(c);
                    }
                } else {
                    specials.add(t);
                }
            }

            // build concat_string: reverse all alphabetical chars and apply alternating caps (start uppercase)
            Collections.reverse(allAlphaChars);
            StringBuilder concat = new StringBuilder();
            boolean upper = true;
            for (char c : allAlphaChars) {
                if (upper) concat.append(Character.toUpperCase(c));
                else concat.append(Character.toLowerCase(c));
                upper = !upper;
            }

            resp.setIs_success(true);
            resp.setUser_id(buildUserId(FULL_NAME, DOB_DDMMYYYY));
            resp.setEmail(EMAIL);
            resp.setRoll_number(ROLL);
            resp.setOdd_numbers(odd);
            resp.setEven_numbers(even);
            resp.setAlphabets(alphabets);
            resp.setSpecial_characters(specials);
            resp.setSum(String.valueOf(sum));
            resp.setConcat_string(concat.toString());

        } catch (Exception ex) {
            resp.setIs_success(false);
            resp.setUser_id(buildUserId(FULL_NAME, DOB_DDMMYYYY));
            resp.setEmail(EMAIL);
            resp.setRoll_number(ROLL);
            resp.setOdd_numbers(Collections.emptyList());
            resp.setEven_numbers(Collections.emptyList());
            resp.setAlphabets(Collections.emptyList());
            resp.setSpecial_characters(Collections.emptyList());
            resp.setSum("0");
            resp.setConcat_string("");
        }
        return resp;
    }

    private String buildUserId(String fullName, String dob) {
        String cleaned = fullName.trim().toLowerCase().replaceAll("\\s+", "_");
        return cleaned + "_" + dob;
    }
}
