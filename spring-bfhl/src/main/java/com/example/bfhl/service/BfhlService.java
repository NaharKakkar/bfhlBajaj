package com.example.bfhl.service;

import com.example.bfhl.dto.RequestDto;
import com.example.bfhl.dto.ResponseDto;

public interface BfhlService {
    ResponseDto process(RequestDto request);
}
