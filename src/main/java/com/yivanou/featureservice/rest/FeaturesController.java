package com.yivanou.featureservice.rest;

import com.yivanou.featureservice.service.FeaturesService;
import com.yivanou.featureservice.service.dto.FeatureDto;
import com.yivanou.featureservice.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/features")
@RequiredArgsConstructor
public class FeaturesController {

    @Autowired
    private FeaturesService service;

    @GetMapping("/{id}")
    public ResponseEntity<FeatureDto> getFeature(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.getFeatureById(id));
    }

    @GetMapping
    public ResponseEntity<List<FeatureDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping(value = "/{id}/quicklook", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte []> getFeatureImage(@PathVariable UUID id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(service.getImageAsBase64(id), headers, HttpStatus.OK);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(Throwable ex, WebRequest request) {
        log.error("Application error: " + request.getDescription(false), ex);

        if (ex instanceof NotFoundException) {
                Object errMsg = new HashMap<String, String>(){{ put("error_message", ex.getMessage()); }};
            return new ResponseEntity<>(errMsg, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
