package com.markeveryday.service;

import com.markeveryday.model.Image;

import java.io.File;

/**
 * @author liming
 */
public interface ImageService {

    Image findById(Long id);

    void deleteById(Long id);

    Long saveImageFile(Long imageId, String contentType, File file);

    void saveImage(Image image);

}
