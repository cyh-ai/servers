package com.integration.view.eneity;


import com.integration.view.BaseEntitys.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;


/**
 * 险种信息
 */
@Entity
@Table(name = "T_insurance_kind")
public class Kind extends BaseEntity {
    private static final long serialVersionUID = 7920503036528719487L;

    /**
     * 险种代码
     */
    @Column(length = 50, nullable = false)
    private String code;

    /**
     * 险种名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 险种简称
     */
    @Column(name = "short_name", nullable = false)
    private String shortName;

    /**
     * 险种海报
     */
    @Column(name = "image_code", length = 50)
    private String imageCode;

    /**
     * 主附险
     */
    @Column(name = "ins_type", nullable = false)
    private Integer insType;

    /**
     * 长短险
     */
    @Column(name = "ins_long_type", nullable = false)
    private Integer insLongType;

    /**
     * 设计类型
     */
    @Column(name = "ins_design_type", nullable = false)
    private Integer insDesignType;

    /**
     * 豁免类型
     */
    @Column(name = "ins_immunity_type", nullable = false)
    private Integer insImmunityType;

    /**
     * 险种类别
     */
    @Column(name = "ins_head_type", nullable = false)
    private Integer insHeadType;

    /**
     * 险种小类
     */
    @Column(name = "ins_class_type", nullable = false)
    private Integer insClassType;

    /**
     * 报备号
     */
    @Column(name = "record_code", length = 50)
    private String recordCode;

    @Column(name = "show_auto_renewal")
    private Boolean showAutoRenewal;
    @Column(name = "show_auto_prepay")
    private Boolean showAutoPrepay;
    @Column(name = "has_death_liability")
    private Boolean hasDeathLiability;
    @Column(name = "rate_adjusted")
    private Boolean rateAdjusted;

    /**
     * 犹豫期天数
     */
    @Column(name = "cooling_off_period")
    private String coolingOffPeriod;

    /**
     * 保单贷款比例
     */
    @Column(name = "policy_loan_ratio")
    private String policyLoanRatio;

    /**
     * 保费定保额
     */
    @Column(name = "premium_coverage")
    private String premiumCoverage;

    /**
     * 保额定保费
     */
    @Column(name = "coverage_premium")
    private String coveragePremium;

    /**
     * 定额定费
     */
    @Column(name = "fixed_coverage_premium")
    private String fixedCoveragePremium;

    /**
     * 是否支持万能账户优先支付功能
     */
    @Column(name = "is_universal_pay")
    private Boolean isUniversalPay;

    /**
     * 出单方式 0-纸质保单 1-电子保单 2-电子保单+纸质保单
     */
    @Column(name = "policy_option_way")
    private String policyOptionWay;

    /**
     * 产品类型：
     * 0-常规产品
     * 1-网销产品
     */
    @Column(name = "kind_type")
    private String kindType;

    /**
     * 缩略图编码
     */
    @Column(name = "icon_code")
    private String iconCode;

    /**
     * 备案编号
     */
    @Column(name = "filing_no")
    private String filingNo;

    /**
     * 险种描述
     */
    @Column
    private String description;

    /**
     * 是否有利益演示显示:1显示，0不显示
     */
    @Column(name = "is_show_benefit")
    private Boolean isShowBenefit;

    /**
     * 是否热卖:1显示，0不显示
     */
    @Column(name = "is_hot_sale")
    private String isHotSale;

    /**
     * 险种是否允许授权无感理赔，1：是；0：否
     */
    @Column(name = "permit_auth_automatic_claim")
    private String permitAuthAutomaticClaim;
    /**
     * * 是否参加基本医疗保险或公费医疗--1是,0否
     */
    @Column(name = "medical_treatment")
    private String medicalTreatment;
    /**
     * 是否参加其他费用补偿型医疗保险--1是,0否
     */
    @Column(name = "compensated_medical")
    private String compensatedMedical;
    /**
     * 是否显示参加其他费用补偿型医疗保险提示--1是,0否
     */
    @Column(name = "is_show_compensated_medical")
    private String isShowCompensatedMedical;

    @Column(name = "risk_coverage_category")
    private String riskCoverageCategory;

    /**
     * 是否为b2c上线险种:1显示，0不显示
     */
    @Column(name = "is_b2c_up")
    private String isB2cUp;

    /**
     * b2c二期排序
     */
    @Column(name = "seq")
    private Integer seq;

    /**
     * 是否客户检查，1投保人老客户检查,2投保人老客户检查+约定险种检查，0不检查，空默认也不检查
     */
    @Column(name = "applicant_customer_check")
    private String applicantCustomerCheck;

    /**
     * 是否客户检查，1被保人老客户检查,2被保人老客户检查+约定险种检查，0不检查，空默认也不检查
     */
    @Column(name = "insured_customer_check")
    private String insuredCustomerCheck;

    /**
     * 是否支持家庭单-1:是 0:否
     */
    @Column(name = "is_family")
    private String isFamily;

    /**
     * 疾病观察期: 无或观察期（X天）
     */
    @Column(name = "observation_period")
    private String observationPeriod;


    /**
     * 是否区分吸烟计费--1是,0否
     */
    @Column(name = "smoke_charging")
    private Boolean smokeCharging;
    /**
     * 是否有健康加费--1是,0否
     */
    @Column(name = "has_health_add_premium")
    private Boolean hasHealthAddPremium;
    /**
     * 是否有职业加费--1是,0否
     */
    @Column(name = "has_career_add_premium")
    private Boolean hasCareerAddPremium;
    /**
     * 医疗险是否有指定医疗机构--1是,0否
     */
    @Column(name = "designated_medical_institution")
    private Boolean designatedMedicalInstitution;
    /**
     * 万能险年保证利率
     */
    @Column(name = "annual_guaranteed_interest_rate")
    private String annualGuaranteedInterestRate;
    /**
     * 是否展示保费表
     */
    @Column(name = "show_premium")
    private String showPremium;

    /**
     * 不支持pad端，1是0否
     */
    @Column(name = "no_support_pad")
    private String noSupportPad;
    /**
     * 不支持多主险
     */
    @Column(name = "no_support_multiple_main_insurance")
    private String noSupportMultipleMainInsurance;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public Integer getInsType() {
        return insType;
    }

    public void setInsType(Integer insType) {
        this.insType = insType;
    }

    public Integer getInsLongType() {
        return insLongType;
    }

    public void setInsLongType(Integer insLongType) {
        this.insLongType = insLongType;
    }

    public Integer getInsDesignType() {
        return insDesignType;
    }

    public void setInsDesignType(Integer insDesignType) {
        this.insDesignType = insDesignType;
    }

    public Integer getInsImmunityType() {
        return insImmunityType;
    }

    public void setInsImmunityType(Integer insImmunityType) {
        this.insImmunityType = insImmunityType;
    }

    public Integer getInsHeadType() {
        return insHeadType;
    }

    public void setInsHeadType(Integer insHeadType) {
        this.insHeadType = insHeadType;
    }

    public Integer getInsClassType() {
        return insClassType;
    }

    public void setInsClassType(Integer insClassType) {
        this.insClassType = insClassType;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public Boolean getShowAutoRenewal() {
        return showAutoRenewal;
    }

    public void setShowAutoRenewal(Boolean showAutoRenewal) {
        this.showAutoRenewal = showAutoRenewal;
    }

    public Boolean getShowAutoPrepay() {
        return showAutoPrepay;
    }

    public void setShowAutoPrepay(Boolean showAutoPrepay) {
        this.showAutoPrepay = showAutoPrepay;
    }

    public Boolean getHasDeathLiability() {
        return hasDeathLiability;
    }

    public void setHasDeathLiability(Boolean hasDeathLiability) {
        this.hasDeathLiability = hasDeathLiability;
    }

    public Boolean getRateAdjusted() {
        return rateAdjusted;
    }

    public void setRateAdjusted(Boolean rateAdjusted) {
        this.rateAdjusted = rateAdjusted;
    }

    public String getCoolingOffPeriod() {
        return coolingOffPeriod;
    }

    public void setCoolingOffPeriod(String coolingOffPeriod) {
        this.coolingOffPeriod = coolingOffPeriod;
    }

    public String getPolicyLoanRatio() {
        return policyLoanRatio;
    }

    public void setPolicyLoanRatio(String policyLoanRatio) {
        this.policyLoanRatio = policyLoanRatio;
    }

    public String getPremiumCoverage() {
        return premiumCoverage;
    }

    public void setPremiumCoverage(String premiumCoverage) {
        this.premiumCoverage = premiumCoverage;
    }

    public String getCoveragePremium() {
        return coveragePremium;
    }

    public void setCoveragePremium(String coveragePremium) {
        this.coveragePremium = coveragePremium;
    }

    public String getFixedCoveragePremium() {
        return fixedCoveragePremium;
    }

    public void setFixedCoveragePremium(String fixedCoveragePremium) {
        this.fixedCoveragePremium = fixedCoveragePremium;
    }

    public Boolean getUniversalPay() {
        return isUniversalPay;
    }

    public void setUniversalPay(Boolean universalPay) {
        isUniversalPay = universalPay;
    }

    public String getPolicyOptionWay() {
        return policyOptionWay;
    }

    public void setPolicyOptionWay(String policyOptionWay) {
        this.policyOptionWay = policyOptionWay;
    }

    public String getKindType() {
        return kindType;
    }

    public void setKindType(String kindType) {
        this.kindType = kindType;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public String getFilingNo() {
        return filingNo;
    }

    public void setFilingNo(String filingNo) {
        this.filingNo = filingNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getShowBenefit() {
        return isShowBenefit;
    }

    public void setShowBenefit(Boolean showBenefit) {
        isShowBenefit = showBenefit;
    }

    public String getIsHotSale() {
        return isHotSale;
    }

    public void setIsHotSale(String isHotSale) {
        this.isHotSale = isHotSale;
    }

    public String getPermitAuthAutomaticClaim() {
        return permitAuthAutomaticClaim;
    }

    public void setPermitAuthAutomaticClaim(String permitAuthAutomaticClaim) {
        this.permitAuthAutomaticClaim = permitAuthAutomaticClaim;
    }

    public String getMedicalTreatment() {
        return medicalTreatment;
    }

    public void setMedicalTreatment(String medicalTreatment) {
        this.medicalTreatment = medicalTreatment;
    }

    public String getCompensatedMedical() {
        return compensatedMedical;
    }

    public void setCompensatedMedical(String compensatedMedical) {
        this.compensatedMedical = compensatedMedical;
    }

    public String getIsShowCompensatedMedical() {
        return isShowCompensatedMedical;
    }

    public void setIsShowCompensatedMedical(String isShowCompensatedMedical) {
        this.isShowCompensatedMedical = isShowCompensatedMedical;
    }

    public String getRiskCoverageCategory() {
        return riskCoverageCategory;
    }

    public void setRiskCoverageCategory(String riskCoverageCategory) {
        this.riskCoverageCategory = riskCoverageCategory;
    }

    public String getIsB2cUp() {
        return isB2cUp;
    }

    public void setIsB2cUp(String isB2cUp) {
        this.isB2cUp = isB2cUp;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getApplicantCustomerCheck() {
        return applicantCustomerCheck;
    }

    public void setApplicantCustomerCheck(String applicantCustomerCheck) {
        this.applicantCustomerCheck = applicantCustomerCheck;
    }

    public String getInsuredCustomerCheck() {
        return insuredCustomerCheck;
    }

    public void setInsuredCustomerCheck(String insuredCustomerCheck) {
        this.insuredCustomerCheck = insuredCustomerCheck;
    }

    public String getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(String isFamily) {
        this.isFamily = isFamily;
    }

    public String getObservationPeriod() {
        return observationPeriod;
    }

    public void setObservationPeriod(String observationPeriod) {
        this.observationPeriod = observationPeriod;
    }

    public Boolean getSmokeCharging() {
        return smokeCharging;
    }

    public void setSmokeCharging(Boolean smokeCharging) {
        this.smokeCharging = smokeCharging;
    }

    public Boolean getHasHealthAddPremium() {
        return hasHealthAddPremium;
    }

    public void setHasHealthAddPremium(Boolean hasHealthAddPremium) {
        this.hasHealthAddPremium = hasHealthAddPremium;
    }

    public Boolean getHasCareerAddPremium() {
        return hasCareerAddPremium;
    }

    public void setHasCareerAddPremium(Boolean hasCareerAddPremium) {
        this.hasCareerAddPremium = hasCareerAddPremium;
    }

    public Boolean getDesignatedMedicalInstitution() {
        return designatedMedicalInstitution;
    }

    public void setDesignatedMedicalInstitution(Boolean designatedMedicalInstitution) {
        this.designatedMedicalInstitution = designatedMedicalInstitution;
    }

    public String getAnnualGuaranteedInterestRate() {
        return annualGuaranteedInterestRate;
    }

    public void setAnnualGuaranteedInterestRate(String annualGuaranteedInterestRate) {
        this.annualGuaranteedInterestRate = annualGuaranteedInterestRate;
    }

    public String getShowPremium() {
        return showPremium;
    }

    public void setShowPremium(String showPremium) {
        this.showPremium = showPremium;
    }

    public String getNoSupportPad() {
        return noSupportPad;
    }

    public void setNoSupportPad(String noSupportPad) {
        this.noSupportPad = noSupportPad;
    }

    public String getNoSupportMultipleMainInsurance() {
        return noSupportMultipleMainInsurance;
    }

    public void setNoSupportMultipleMainInsurance(String noSupportMultipleMainInsurance) {
        this.noSupportMultipleMainInsurance = noSupportMultipleMainInsurance;
    }
}





