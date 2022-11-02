package com.gigadev.digitalmarketplace.downloadcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadCodeRepo extends JpaRepository<DownloadCode, Long> {

}
