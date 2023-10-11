package ru.practicum.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
public class RequestsReportDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}