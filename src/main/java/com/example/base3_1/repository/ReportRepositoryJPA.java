package com.example.base3_1.repository;

import com.example.base3_1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepositoryJPA extends JpaRepository<User, Long> {

    @Query(value = "CALL Proc_GETReportInspectorQuality_total(?, ?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    Integer callGetReportAcdCallCG002Total(
            Object fromDate,
            Object toDate,
            Object channelIds,
            Object channelNames,
            Object markLevels,
            Object marlLevelNames,
            Object kzAccountId,
            Object realmName
    );

    @Query(value = "CALL Proc_ReportErrorLine(?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    List<Object> getReportErrorLine(Object fromDate, Object toDate, Object channelIds, Object sourceIds, Object kzAccountId, Object groupId, Object realmName);


    @Query(value = "CALL scoringStructureReportByDuration_Total(?, ?, ?, ?, ?)", nativeQuery = true)
    Long callScoringStructureByDurationReport(
            Object fromTimeMs,
            Object toTimeMs,
            Object realmName,
            Object channelIds,
            Object sourceCodes
    );

    @Query(value = "CALL scoringStructureReportByType_Total(?, ?, ?, ?, ?)", nativeQuery = true)
    Long callScoringStructureByTypeReport(
            Object fromTimeMs,
            Object toTimeMs,
            Object realmName,
            Object channelIds,
            Object sourceCodes
    );

    @Query(value = "CALL GETResultQualityReport(?, ?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    List<Object> getResultQualityReport(
            Object kzAccountId,
            Object fromDate,
            Object toDate,
            Object listChannelId,
            Object listCallee,
            Object listAgentId,
            Object realmName,
            Object groupId
    );

    @Query(value = "CALL qualifierCriteriaReport_Total(?, ?, ?, ?, ?)", nativeQuery = true)
    Long callCriteriaReport(
            Object fromTimeMs,
            Object toTimeMs,
            Object realmName,
            Object channelIds,
            Object sourceCodes
    );

    @Query(value = "CALL GetDetailScoringAgentReport(?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    List<Object> getResultDetailScoringReport(
            Object fromDate,
            Object toDate,
            Object realmName,
            Object groupId,
            Object listChannelId,
            Object listSourceCode,
            Object listAgentId
    );

    @Query(value = "CALL bc_04_compare_quality_two_consecutive_months(?,?,? ?,?, ?,?,?,?)", nativeQuery = true)
    List<Object> bc_04_compare_quality_two_consecutive_months(
            Object monthStr,
            Object realmName,
            Object groupId,
            Object kzAccountId,
            Object groupType,
            Object apParamCodeRegionList,
            Object apParamCodeSeniorityList,
            Object sourceCodeList,
            Object channelIdList
    );
    @Query(value = "CALL PROC_GRADUATEQUALITYREPORT(?, ?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    List<Object> getGraduateQualityReport(
            Object p1,
            Object p2,
            Object p3,
            Object p4,
            Object p5,
            Object p6,
            Object p7,
            Object p8
    );
    @Query(value = "CALL PROC_CallEmotionQuality(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    List<Object> getEmotionQualityCallReport(
            Object p1,
            Object p2,
            Object p3,
            Object p4,
            Object p5,
            Object p6,
            Object p7,
            Object p8,
            Object p9,
            Object p10,
            Object p11,
            Object p12
    );

}
