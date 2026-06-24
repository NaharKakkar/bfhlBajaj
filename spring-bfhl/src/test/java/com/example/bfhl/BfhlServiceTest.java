package com.example.bfhl;

import com.example.bfhl.dto.RequestDto;
import com.example.bfhl.dto.ResponseDto;
import com.example.bfhl.service.BfhlService;
import com.example.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BfhlServiceTest {

    private BfhlService service;

    @BeforeEach
    public void setup() {
        service = new BfhlServiceImpl();
    }

    // ─── Example A ───
    @Test
    public void exampleA() {
        RequestDto req = new RequestDto(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDto res = service.process(req);

        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("339", res.getSum());
        Assertions.assertEquals(Arrays.asList("1"), res.getOdd_numbers());
        Assertions.assertEquals(Arrays.asList("334", "4"), res.getEven_numbers());
        Assertions.assertEquals(Arrays.asList("A", "R"), res.getAlphabets());
        Assertions.assertEquals(Arrays.asList("$"), res.getSpecial_characters());
        Assertions.assertEquals("Ra", res.getConcat_string());
    }

    // ─── Example B ───
    @Test
    public void exampleB() {
        RequestDto req = new RequestDto(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        ResponseDto res = service.process(req);

        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("103", res.getSum());
        Assertions.assertEquals(Arrays.asList("5"), res.getOdd_numbers());
        Assertions.assertEquals(Arrays.asList("2", "4", "92"), res.getEven_numbers());
        Assertions.assertEquals(Arrays.asList("A", "Y", "B"), res.getAlphabets());
        Assertions.assertEquals(Arrays.asList("&", "-", "*"), res.getSpecial_characters());
        Assertions.assertEquals("ByA", res.getConcat_string());
    }

    // ─── Example C (multi-char alphabetical tokens) ───
    @Test
    public void exampleC() {
        RequestDto req = new RequestDto(Arrays.asList("A", "ABCD", "DOE"));
        ResponseDto res = service.process(req);

        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("0", res.getSum());
        Assertions.assertTrue(res.getOdd_numbers().isEmpty());
        Assertions.assertTrue(res.getEven_numbers().isEmpty());
        Assertions.assertEquals(Arrays.asList("A", "ABCD", "DOE"), res.getAlphabets());
        Assertions.assertTrue(res.getSpecial_characters().isEmpty());
        // chars: A,A,B,C,D,D,O,E  reversed: E,O,D,D,C,B,A,A  alt-caps: E,o,D,d,C,b,A,a
        Assertions.assertEquals("EoDdCbAa", res.getConcat_string());
    }

    // ─── Edge case: empty data list ───
    @Test
    public void emptyData() {
        RequestDto req = new RequestDto(Collections.emptyList());
        ResponseDto res = service.process(req);

        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("0", res.getSum());
        Assertions.assertTrue(res.getOdd_numbers().isEmpty());
        Assertions.assertTrue(res.getEven_numbers().isEmpty());
        Assertions.assertTrue(res.getAlphabets().isEmpty());
        Assertions.assertTrue(res.getSpecial_characters().isEmpty());
        Assertions.assertEquals("", res.getConcat_string());
    }

    // ─── Edge case: null data field ───
    @Test
    public void nullData() {
        RequestDto req = new RequestDto(null);
        ResponseDto res = service.process(req);

        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("0", res.getSum());
        Assertions.assertTrue(res.getOdd_numbers().isEmpty());
        Assertions.assertTrue(res.getEven_numbers().isEmpty());
        Assertions.assertTrue(res.getAlphabets().isEmpty());
        Assertions.assertTrue(res.getSpecial_characters().isEmpty());
        Assertions.assertEquals("", res.getConcat_string());
    }

    // ─── Only numbers ───
    @Test
    public void onlyNumbers() {
        RequestDto req = new RequestDto(Arrays.asList("1", "2", "3", "100"));
        ResponseDto res = service.process(req);

        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("106", res.getSum());
        Assertions.assertEquals(Arrays.asList("1", "3"), res.getOdd_numbers());
        Assertions.assertEquals(Arrays.asList("2", "100"), res.getEven_numbers());
        Assertions.assertTrue(res.getAlphabets().isEmpty());
        Assertions.assertTrue(res.getSpecial_characters().isEmpty());
        Assertions.assertEquals("", res.getConcat_string());
    }

    // ─── Only special characters ───
    @Test
    public void onlySpecials() {
        RequestDto req = new RequestDto(Arrays.asList("@", "#", "!", "%"));
        ResponseDto res = service.process(req);

        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("0", res.getSum());
        Assertions.assertTrue(res.getOdd_numbers().isEmpty());
        Assertions.assertTrue(res.getEven_numbers().isEmpty());
        Assertions.assertTrue(res.getAlphabets().isEmpty());
        Assertions.assertEquals(Arrays.asList("@", "#", "!", "%"), res.getSpecial_characters());
        Assertions.assertEquals("", res.getConcat_string());
    }

    // ─── user_id format verification ───
    @Test
    public void userIdFormat() {
        RequestDto req = new RequestDto(Arrays.asList("1"));
        ResponseDto res = service.process(req);

        // user_id should be lowercase_name_ddmmyyyy
        Assertions.assertNotNull(res.getUser_id());
        Assertions.assertTrue(res.getUser_id().matches("^[a-z_]+_\\d{8}$"),
                "user_id must match pattern: lowercase_name_ddmmyyyy");
    }

    // ─── Single alphabetical character ───
    @Test
    public void singleAlphaChar() {
        RequestDto req = new RequestDto(Arrays.asList("z"));
        ResponseDto res = service.process(req);

        Assertions.assertEquals(Arrays.asList("Z"), res.getAlphabets());
        Assertions.assertEquals("Z", res.getConcat_string());
    }

    // ─── Numbers returned as strings ───
    @Test
    public void numbersReturnedAsStrings() {
        RequestDto req = new RequestDto(Arrays.asList("10", "7"));
        ResponseDto res = service.process(req);

        Assertions.assertEquals("17", res.getSum());
        // elements are strings, not integers
        Assertions.assertEquals("10", res.getEven_numbers().get(0));
        Assertions.assertEquals("7", res.getOdd_numbers().get(0));
    }
}
