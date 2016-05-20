package com.markeveryday.service.impl;

import com.markeveryday.dao.ImageDao;
import com.markeveryday.model.Image;
import com.markeveryday.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * @author liming
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image findById(Long id) {
        if (id == null) {
            return null;
        }
        return imageDao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Image image = findById(id);
        if (image == null) {
            return;
        }
        image.setDeleteStatus(true);
        image.setModTime(new Date());
        imageDao.saveOrUpdate(image);

    }

    @Override
    public Long saveImageFile(Long imageId, String contentType, File file) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));

            long fileLength = file.length();

            byte[] imageData = new byte[(int) fileLength];

            dataInputStream.read(imageData, 0, (int) fileLength);

            Image image = null;
            if (imageId == null || (imageId != null && imageId == 0)) {
                image = new Image();
                image.setCreateTime(new Date());
                image.setModTime(new Date());
                image.setDeleteStatus(false);
                image.setImage(imageData);
                image.setContentType(contentType);
                image.setLength(fileLength);
            } else {
                image = findById(imageId);
                image.setModTime(new Date());
                image.setImage(imageData);
                image.setContentType(contentType);
                image.setLength(fileLength);
            }
            saveImage(image);
            return image.getId();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public void saveImage(Image image) {
        imageDao.saveOrUpdate(image);
    }
}
