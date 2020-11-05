package com.ust.shbay.manager.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MeetingBookingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MeetingBookingExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andMeetingIdIsNull() {
            addCriterion("meeting_id is null");
            return (Criteria) this;
        }

        public Criteria andMeetingIdIsNotNull() {
            addCriterion("meeting_id is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingIdEqualTo(Integer value) {
            addCriterion("meeting_id =", value, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdNotEqualTo(Integer value) {
            addCriterion("meeting_id <>", value, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdGreaterThan(Integer value) {
            addCriterion("meeting_id >", value, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("meeting_id >=", value, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdLessThan(Integer value) {
            addCriterion("meeting_id <", value, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdLessThanOrEqualTo(Integer value) {
            addCriterion("meeting_id <=", value, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdIn(List<Integer> values) {
            addCriterion("meeting_id in", values, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdNotIn(List<Integer> values) {
            addCriterion("meeting_id not in", values, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdBetween(Integer value1, Integer value2) {
            addCriterion("meeting_id between", value1, value2, "meetingId");
            return (Criteria) this;
        }

        public Criteria andMeetingIdNotBetween(Integer value1, Integer value2) {
            addCriterion("meeting_id not between", value1, value2, "meetingId");
            return (Criteria) this;
        }

        public Criteria andVisitDateIsNull() {
            addCriterion("visit_date is null");
            return (Criteria) this;
        }

        public Criteria andVisitDateIsNotNull() {
            addCriterion("visit_date is not null");
            return (Criteria) this;
        }

        public Criteria andVisitDateEqualTo(Date value) {
            addCriterionForJDBCDate("visit_date =", value, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("visit_date <>", value, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateGreaterThan(Date value) {
            addCriterionForJDBCDate("visit_date >", value, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("visit_date >=", value, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateLessThan(Date value) {
            addCriterionForJDBCDate("visit_date <", value, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("visit_date <=", value, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateIn(List<Date> values) {
            addCriterionForJDBCDate("visit_date in", values, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("visit_date not in", values, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("visit_date between", value1, value2, "visitDate");
            return (Criteria) this;
        }

        public Criteria andVisitDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("visit_date not between", value1, value2, "visitDate");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("begin_time is null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeEqualTo(String value) {
            addCriterion("begin_time =", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotEqualTo(String value) {
            addCriterion("begin_time <>", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThan(String value) {
            addCriterion("begin_time >", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(String value) {
            addCriterion("begin_time >=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThan(String value) {
            addCriterion("begin_time <", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(String value) {
            addCriterion("begin_time <=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLike(String value) {
            addCriterion("begin_time like", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotLike(String value) {
            addCriterion("begin_time not like", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIn(List<String> values) {
            addCriterion("begin_time in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotIn(List<String> values) {
            addCriterion("begin_time not in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeBetween(String value1, String value2) {
            addCriterion("begin_time between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotBetween(String value1, String value2) {
            addCriterion("begin_time not between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andBookingNumberIsNull() {
            addCriterion("booking_number is null");
            return (Criteria) this;
        }

        public Criteria andBookingNumberIsNotNull() {
            addCriterion("booking_number is not null");
            return (Criteria) this;
        }

        public Criteria andBookingNumberEqualTo(Integer value) {
            addCriterion("booking_number =", value, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberNotEqualTo(Integer value) {
            addCriterion("booking_number <>", value, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberGreaterThan(Integer value) {
            addCriterion("booking_number >", value, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("booking_number >=", value, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberLessThan(Integer value) {
            addCriterion("booking_number <", value, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberLessThanOrEqualTo(Integer value) {
            addCriterion("booking_number <=", value, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberIn(List<Integer> values) {
            addCriterion("booking_number in", values, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberNotIn(List<Integer> values) {
            addCriterion("booking_number not in", values, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberBetween(Integer value1, Integer value2) {
            addCriterion("booking_number between", value1, value2, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andBookingNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("booking_number not between", value1, value2, "bookingNumber");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdIsNull() {
            addCriterion("company_type_id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdIsNotNull() {
            addCriterion("company_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdEqualTo(Integer value) {
            addCriterion("company_type_id =", value, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdNotEqualTo(Integer value) {
            addCriterion("company_type_id <>", value, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdGreaterThan(Integer value) {
            addCriterion("company_type_id >", value, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_type_id >=", value, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdLessThan(Integer value) {
            addCriterion("company_type_id <", value, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("company_type_id <=", value, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdIn(List<Integer> values) {
            addCriterion("company_type_id in", values, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdNotIn(List<Integer> values) {
            addCriterion("company_type_id not in", values, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("company_type_id between", value1, value2, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("company_type_id not between", value1, value2, "companyTypeId");
            return (Criteria) this;
        }

        public Criteria andBookingPersionIsNull() {
            addCriterion("booking_persion is null");
            return (Criteria) this;
        }

        public Criteria andBookingPersionIsNotNull() {
            addCriterion("booking_persion is not null");
            return (Criteria) this;
        }

        public Criteria andBookingPersionEqualTo(String value) {
            addCriterion("booking_persion =", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionNotEqualTo(String value) {
            addCriterion("booking_persion <>", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionGreaterThan(String value) {
            addCriterion("booking_persion >", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionGreaterThanOrEqualTo(String value) {
            addCriterion("booking_persion >=", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionLessThan(String value) {
            addCriterion("booking_persion <", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionLessThanOrEqualTo(String value) {
            addCriterion("booking_persion <=", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionLike(String value) {
            addCriterion("booking_persion like", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionNotLike(String value) {
            addCriterion("booking_persion not like", value, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionIn(List<String> values) {
            addCriterion("booking_persion in", values, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionNotIn(List<String> values) {
            addCriterion("booking_persion not in", values, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionBetween(String value1, String value2) {
            addCriterion("booking_persion between", value1, value2, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPersionNotBetween(String value1, String value2) {
            addCriterion("booking_persion not between", value1, value2, "bookingPersion");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneIsNull() {
            addCriterion("booking_phone is null");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneIsNotNull() {
            addCriterion("booking_phone is not null");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneEqualTo(String value) {
            addCriterion("booking_phone =", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneNotEqualTo(String value) {
            addCriterion("booking_phone <>", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneGreaterThan(String value) {
            addCriterion("booking_phone >", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("booking_phone >=", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneLessThan(String value) {
            addCriterion("booking_phone <", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneLessThanOrEqualTo(String value) {
            addCriterion("booking_phone <=", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneLike(String value) {
            addCriterion("booking_phone like", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneNotLike(String value) {
            addCriterion("booking_phone not like", value, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneIn(List<String> values) {
            addCriterion("booking_phone in", values, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneNotIn(List<String> values) {
            addCriterion("booking_phone not in", values, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneBetween(String value1, String value2) {
            addCriterion("booking_phone between", value1, value2, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andBookingPhoneNotBetween(String value1, String value2) {
            addCriterion("booking_phone not between", value1, value2, "bookingPhone");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdIsNull() {
            addCriterion("put_requirement_pic_id is null");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdIsNotNull() {
            addCriterion("put_requirement_pic_id is not null");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdEqualTo(String value) {
            addCriterion("put_requirement_pic_id =", value, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdNotEqualTo(String value) {
            addCriterion("put_requirement_pic_id <>", value, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdGreaterThan(String value) {
            addCriterion("put_requirement_pic_id >", value, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdGreaterThanOrEqualTo(String value) {
            addCriterion("put_requirement_pic_id >=", value, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdLessThan(String value) {
            addCriterion("put_requirement_pic_id <", value, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdLessThanOrEqualTo(String value) {
            addCriterion("put_requirement_pic_id <=", value, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdIn(List<String> values) {
            addCriterion("put_requirement_pic_id in", values, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdNotIn(List<String> values) {
            addCriterion("put_requirement_pic_id not in", values, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdBetween(String value1, String value2) {
            addCriterion("put_requirement_pic_id between", value1, value2, "putRequirementPicId");
            return (Criteria) this;
        }

        public Criteria andPutRequirementPicIdNotBetween(String value1, String value2) {
            addCriterion("put_requirement_pic_id not between", value1, value2, "putRequirementPicId");
            return (Criteria) this;
        }

//        public Criteria andComputerNumIsNull() {
//            addCriterion("computer_num is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumIsNotNull() {
//            addCriterion("computer_num is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumEqualTo(Integer value) {
//            addCriterion("computer_num =", value, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumNotEqualTo(Integer value) {
//            addCriterion("computer_num <>", value, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumGreaterThan(Integer value) {
//            addCriterion("computer_num >", value, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumGreaterThanOrEqualTo(Integer value) {
//            addCriterion("computer_num >=", value, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumLessThan(Integer value) {
//            addCriterion("computer_num <", value, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumLessThanOrEqualTo(Integer value) {
//            addCriterion("computer_num <=", value, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumIn(List<Integer> values) {
//            addCriterion("computer_num in", values, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumNotIn(List<Integer> values) {
//            addCriterion("computer_num not in", values, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumBetween(Integer value1, Integer value2) {
//            addCriterion("computer_num between", value1, value2, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andComputerNumNotBetween(Integer value1, Integer value2) {
//            addCriterion("computer_num not between", value1, value2, "computerNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumIsNull() {
//            addCriterion("teca_num is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumIsNotNull() {
//            addCriterion("teca_num is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumEqualTo(Integer value) {
//            addCriterion("teca_num =", value, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumNotEqualTo(Integer value) {
//            addCriterion("teca_num <>", value, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumGreaterThan(Integer value) {
//            addCriterion("teca_num >", value, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumGreaterThanOrEqualTo(Integer value) {
//            addCriterion("teca_num >=", value, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumLessThan(Integer value) {
//            addCriterion("teca_num <", value, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumLessThanOrEqualTo(Integer value) {
//            addCriterion("teca_num <=", value, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumIn(List<Integer> values) {
//            addCriterion("teca_num in", values, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumNotIn(List<Integer> values) {
//            addCriterion("teca_num not in", values, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumBetween(Integer value1, Integer value2) {
//            addCriterion("teca_num between", value1, value2, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andTecaNumNotBetween(Integer value1, Integer value2) {
//            addCriterion("teca_num not between", value1, value2, "tecaNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumIsNull() {
//            addCriterion("projection_num is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumIsNotNull() {
//            addCriterion("projection_num is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumEqualTo(Integer value) {
//            addCriterion("projection_num =", value, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumNotEqualTo(Integer value) {
//            addCriterion("projection_num <>", value, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumGreaterThan(Integer value) {
//            addCriterion("projection_num >", value, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumGreaterThanOrEqualTo(Integer value) {
//            addCriterion("projection_num >=", value, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumLessThan(Integer value) {
//            addCriterion("projection_num <", value, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumLessThanOrEqualTo(Integer value) {
//            addCriterion("projection_num <=", value, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumIn(List<Integer> values) {
//            addCriterion("projection_num in", values, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumNotIn(List<Integer> values) {
//            addCriterion("projection_num not in", values, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumBetween(Integer value1, Integer value2) {
//            addCriterion("projection_num between", value1, value2, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andProjectionNumNotBetween(Integer value1, Integer value2) {
//            addCriterion("projection_num not between", value1, value2, "projectionNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumIsNull() {
//            addCriterion("hand_microphone_num is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumIsNotNull() {
//            addCriterion("hand_microphone_num is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumEqualTo(Integer value) {
//            addCriterion("hand_microphone_num =", value, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumNotEqualTo(Integer value) {
//            addCriterion("hand_microphone_num <>", value, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumGreaterThan(Integer value) {
//            addCriterion("hand_microphone_num >", value, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumGreaterThanOrEqualTo(Integer value) {
//            addCriterion("hand_microphone_num >=", value, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumLessThan(Integer value) {
//            addCriterion("hand_microphone_num <", value, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumLessThanOrEqualTo(Integer value) {
//            addCriterion("hand_microphone_num <=", value, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumIn(List<Integer> values) {
//            addCriterion("hand_microphone_num in", values, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumNotIn(List<Integer> values) {
//            addCriterion("hand_microphone_num not in", values, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumBetween(Integer value1, Integer value2) {
//            addCriterion("hand_microphone_num between", value1, value2, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andHandMicrophoneNumNotBetween(Integer value1, Integer value2) {
//            addCriterion("hand_microphone_num not between", value1, value2, "handMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumIsNull() {
//            addCriterion("gooseneck_microphone_num is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumIsNotNull() {
//            addCriterion("gooseneck_microphone_num is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumEqualTo(Integer value) {
//            addCriterion("gooseneck_microphone_num =", value, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumNotEqualTo(Integer value) {
//            addCriterion("gooseneck_microphone_num <>", value, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumGreaterThan(Integer value) {
//            addCriterion("gooseneck_microphone_num >", value, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumGreaterThanOrEqualTo(Integer value) {
//            addCriterion("gooseneck_microphone_num >=", value, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumLessThan(Integer value) {
//            addCriterion("gooseneck_microphone_num <", value, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumLessThanOrEqualTo(Integer value) {
//            addCriterion("gooseneck_microphone_num <=", value, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumIn(List<Integer> values) {
//            addCriterion("gooseneck_microphone_num in", values, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumNotIn(List<Integer> values) {
//            addCriterion("gooseneck_microphone_num not in", values, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumBetween(Integer value1, Integer value2) {
//            addCriterion("gooseneck_microphone_num between", value1, value2, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }
//
//        public Criteria andGooseneckMicrophoneNumNotBetween(Integer value1, Integer value2) {
//            addCriterion("gooseneck_microphone_num not between", value1, value2, "gooseneckMicrophoneNum");
//            return (Criteria) this;
//        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPassagewayIsNull() {
            addCriterion("passageway is null");
            return (Criteria) this;
        }

        public Criteria andPassagewayIsNotNull() {
            addCriterion("passageway is not null");
            return (Criteria) this;
        }

        public Criteria andPassagewayEqualTo(Integer value) {
            addCriterion("passageway =", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayNotEqualTo(Integer value) {
            addCriterion("passageway <>", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayGreaterThan(Integer value) {
            addCriterion("passageway >", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayGreaterThanOrEqualTo(Integer value) {
            addCriterion("passageway >=", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayLessThan(Integer value) {
            addCriterion("passageway <", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayLessThanOrEqualTo(Integer value) {
            addCriterion("passageway <=", value, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayIn(List<Integer> values) {
            addCriterion("passageway in", values, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayNotIn(List<Integer> values) {
            addCriterion("passageway not in", values, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayBetween(Integer value1, Integer value2) {
            addCriterion("passageway between", value1, value2, "passageway");
            return (Criteria) this;
        }

        public Criteria andPassagewayNotBetween(Integer value1, Integer value2) {
            addCriterion("passageway not between", value1, value2, "passageway");
            return (Criteria) this;
        }

        public Criteria andCancelChannelIsNull() {
            addCriterion("cancel_channel is null");
            return (Criteria) this;
        }

        public Criteria andCancelChannelIsNotNull() {
            addCriterion("cancel_channel is not null");
            return (Criteria) this;
        }

        public Criteria andCancelChannelEqualTo(Integer value) {
            addCriterion("cancel_channel =", value, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelNotEqualTo(Integer value) {
            addCriterion("cancel_channel <>", value, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelGreaterThan(Integer value) {
            addCriterion("cancel_channel >", value, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_channel >=", value, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelLessThan(Integer value) {
            addCriterion("cancel_channel <", value, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_channel <=", value, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelIn(List<Integer> values) {
            addCriterion("cancel_channel in", values, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelNotIn(List<Integer> values) {
            addCriterion("cancel_channel not in", values, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelBetween(Integer value1, Integer value2) {
            addCriterion("cancel_channel between", value1, value2, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelChannelNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_channel not between", value1, value2, "cancelChannel");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdIsNull() {
            addCriterion("cancel_reason_id is null");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdIsNotNull() {
            addCriterion("cancel_reason_id is not null");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdEqualTo(Integer value) {
            addCriterion("cancel_reason_id =", value, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdNotEqualTo(Integer value) {
            addCriterion("cancel_reason_id <>", value, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdGreaterThan(Integer value) {
            addCriterion("cancel_reason_id >", value, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_reason_id >=", value, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdLessThan(Integer value) {
            addCriterion("cancel_reason_id <", value, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_reason_id <=", value, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdIn(List<Integer> values) {
            addCriterion("cancel_reason_id in", values, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdNotIn(List<Integer> values) {
            addCriterion("cancel_reason_id not in", values, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdBetween(Integer value1, Integer value2) {
            addCriterion("cancel_reason_id between", value1, value2, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_reason_id not between", value1, value2, "cancelReasonId");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNull() {
            addCriterion("cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNotNull() {
            addCriterion("cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeEqualTo(Date value) {
            addCriterion("cancel_time =", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotEqualTo(Date value) {
            addCriterion("cancel_time <>", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThan(Date value) {
            addCriterion("cancel_time >", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cancel_time >=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThan(Date value) {
            addCriterion("cancel_time <", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThanOrEqualTo(Date value) {
            addCriterion("cancel_time <=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIn(List<Date> values) {
            addCriterion("cancel_time in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotIn(List<Date> values) {
            addCriterion("cancel_time not in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeBetween(Date value1, Date value2) {
            addCriterion("cancel_time between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotBetween(Date value1, Date value2) {
            addCriterion("cancel_time not between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andActualNumIsNull() {
            addCriterion("actual_num is null");
            return (Criteria) this;
        }

        public Criteria andActualNumIsNotNull() {
            addCriterion("actual_num is not null");
            return (Criteria) this;
        }

        public Criteria andActualNumEqualTo(Integer value) {
            addCriterion("actual_num =", value, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumNotEqualTo(Integer value) {
            addCriterion("actual_num <>", value, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumGreaterThan(Integer value) {
            addCriterion("actual_num >", value, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_num >=", value, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumLessThan(Integer value) {
            addCriterion("actual_num <", value, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumLessThanOrEqualTo(Integer value) {
            addCriterion("actual_num <=", value, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumIn(List<Integer> values) {
            addCriterion("actual_num in", values, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumNotIn(List<Integer> values) {
            addCriterion("actual_num not in", values, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumBetween(Integer value1, Integer value2) {
            addCriterion("actual_num between", value1, value2, "actualNum");
            return (Criteria) this;
        }

        public Criteria andActualNumNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_num not between", value1, value2, "actualNum");
            return (Criteria) this;
        }

        public Criteria andAppraiseIsNull() {
            addCriterion("appraise is null");
            return (Criteria) this;
        }

        public Criteria andAppraiseIsNotNull() {
            addCriterion("appraise is not null");
            return (Criteria) this;
        }

        public Criteria andAppraiseEqualTo(Integer value) {
            addCriterion("appraise =", value, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseNotEqualTo(Integer value) {
            addCriterion("appraise <>", value, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseGreaterThan(Integer value) {
            addCriterion("appraise >", value, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseGreaterThanOrEqualTo(Integer value) {
            addCriterion("appraise >=", value, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseLessThan(Integer value) {
            addCriterion("appraise <", value, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseLessThanOrEqualTo(Integer value) {
            addCriterion("appraise <=", value, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseIn(List<Integer> values) {
            addCriterion("appraise in", values, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseNotIn(List<Integer> values) {
            addCriterion("appraise not in", values, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseBetween(Integer value1, Integer value2) {
            addCriterion("appraise between", value1, value2, "appraise");
            return (Criteria) this;
        }

        public Criteria andAppraiseNotBetween(Integer value1, Integer value2) {
            addCriterion("appraise not between", value1, value2, "appraise");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}