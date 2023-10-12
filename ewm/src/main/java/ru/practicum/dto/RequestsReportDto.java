package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestsReportDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}