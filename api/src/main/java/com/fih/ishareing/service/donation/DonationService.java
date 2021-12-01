package com.fih.ishareing.service.donation;

import com.fih.ishareing.service.donation.model.DonationNposAddVO;
import com.fih.ishareing.service.donation.model.DonationNposUpdateVO;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.fih.ishareing.repository.entity.tbCoreDonationNpo;

import java.util.List;

public interface DonationService {

  JSONObject listDonationNpos(Specification<tbCoreDonationNpo> spec, Pageable pageable);

  List<?> addDonationNpos(List<DonationNposAddVO> nposAddVOList);

  List<?> updateDonationNpos(List<DonationNposUpdateVO> nposAddVOList);

  List<?> deleteDonationNpos(List<String> nposList);

  boolean exist(Integer id);

  void updateDonationIcon(Integer npoId, String icon);

  void removeDonationIcon(Integer npoId);
}