package com.example.bfhl;

import com.example.bfhl.dto.RequestDto;
import com.example.bfhl.dto.ResponseDto;
import com.example.bfhl.service.BfhlService;
import com.example.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BfhlServiceTest {

    private final BfhlService service = new BfhlServiceImpl();

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

    @Test
    public void exampleC() {
        RequestDto req = new RequestDto(Arrays.asList("A", "ABCD", "DOE"));
        ResponseDto res = service.process(req);
        Assertions.assertTrue(res.isIs_success());
        Assertions.assertEquals("0", res.getSum());
        Assertions.assertEquals(Arrays.asList("A", "ABCD", "DOE"), res.getAlphabets());
        Assertions.assertEquals("EoDdCbAa", res.getConcat_string());
    }
}
