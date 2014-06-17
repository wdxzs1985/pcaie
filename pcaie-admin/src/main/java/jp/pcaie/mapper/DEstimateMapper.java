package jp.pcaie.mapper;

import java.util.Map;

import jp.pcaie.domain.EstimateBean;

public interface DEstimateMapper {

    EstimateBean fetchBean(Map<String, Object> param);

    void insert(EstimateBean estimateBean);

    void delete(EstimateBean estimateBean);

}
