package com.shivamtaneja.devconnect.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.shivamtaneja.devconnect.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AzureBlobService {
  private final BlobServiceClient blobServiceClient;

  public String uploadFile(MultipartFile file, String containerName, String profileID) throws IOException {
    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

    if (!containerClient.exists()) {
      throw new NotFoundException("Container not found!");
    }

    BlobClient blobClient = containerClient.getBlobClient(profileID);
    blobClient.upload(file.getInputStream(), file.getSize(), true);

    return blobClient.getBlobUrl();
  }
}
