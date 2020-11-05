package com.ust.shbay.manager.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MeetingExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("`position` is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("`position` is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(String value) {
            addCriterion("`position` =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(String value) {
            addCriterion("`position` <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(String value) {
            addCriterion("`position` >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(String value) {
            addCriterion("`position` >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(String value) {
            addCriterion("`position` <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(String value) {
            addCriterion("`position` <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLike(String value) {
            addCriterion("`position` like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotLike(String value) {
            addCriterion("`position` not like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<String> values) {
            addCriterion("`position` in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<String> values) {
            addCriterion("`position` not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(String value1, String value2) {
            addCriterion("`position` between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(String value1, String value2) {
            addCriterion("`position` not between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(BigDecimal value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(BigDecimal value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(BigDecimal value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(BigDecimal value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<BigDecimal> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<BigDecimal> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andSeatNumIsNull() {
            addCriterion("seat_num is null");
            return (Criteria) this;
        }

        public Criteria andSeatNumIsNotNull() {
            addCriterion("seat_num is not null");
            return (Criteria) this;
        }

        public Criteria andSeatNumEqualTo(Integer value) {
            addCriterion("seat_num =", value, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumNotEqualTo(Integer value) {
            addCriterion("seat_num <>", value, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumGreaterThan(Integer value) {
            addCriterion("seat_num >", value, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("seat_num >=", value, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumLessThan(Integer value) {
            addCriterion("seat_num <", value, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumLessThanOrEqualTo(Integer value) {
            addCriterion("seat_num <=", value, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumIn(List<Integer> values) {
            addCriterion("seat_num in", values, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumNotIn(List<Integer> values) {
            addCriterion("seat_num not in", values, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumBetween(Integer value1, Integer value2) {
            addCriterion("seat_num between", value1, value2, "seatNum");
            return (Criteria) this;
        }

        public Criteria andSeatNumNotBetween(Integer value1, Integer value2) {
            addCriterion("seat_num not between", value1, value2, "seatNum");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeIsNull() {
            addCriterion("am_begin_time is null");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeIsNotNull() {
            addCriterion("am_begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeEqualTo(String value) {
            addCriterion("am_begin_time =", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeNotEqualTo(String value) {
            addCriterion("am_begin_time <>", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeGreaterThan(String value) {
            addCriterion("am_begin_time >", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeGreaterThanOrEqualTo(String value) {
            addCriterion("am_begin_time >=", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeLessThan(String value) {
            addCriterion("am_begin_time <", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeLessThanOrEqualTo(String value) {
            addCriterion("am_begin_time <=", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeLike(String value) {
            addCriterion("am_begin_time like", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeNotLike(String value) {
            addCriterion("am_begin_time not like", value, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeIn(List<String> values) {
            addCriterion("am_begin_time in", values, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeNotIn(List<String> values) {
            addCriterion("am_begin_time not in", values, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeBetween(String value1, String value2) {
            addCriterion("am_begin_time between", value1, value2, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmBeginTimeNotBetween(String value1, String value2) {
            addCriterion("am_begin_time not between", value1, value2, "amBeginTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeIsNull() {
            addCriterion("am_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeIsNotNull() {
            addCriterion("am_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeEqualTo(String value) {
            addCriterion("am_end_time =", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeNotEqualTo(String value) {
            addCriterion("am_end_time <>", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeGreaterThan(String value) {
            addCriterion("am_end_time >", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("am_end_time >=", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeLessThan(String value) {
            addCriterion("am_end_time <", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeLessThanOrEqualTo(String value) {
            addCriterion("am_end_time <=", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeLike(String value) {
            addCriterion("am_end_time like", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeNotLike(String value) {
            addCriterion("am_end_time not like", value, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeIn(List<String> values) {
            addCriterion("am_end_time in", values, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeNotIn(List<String> values) {
            addCriterion("am_end_time not in", values, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeBetween(String value1, String value2) {
            addCriterion("am_end_time between", value1, value2, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andAmEndTimeNotBetween(String value1, String value2) {
            addCriterion("am_end_time not between", value1, value2, "amEndTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeIsNull() {
            addCriterion("pm_begin_time is null");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeIsNotNull() {
            addCriterion("pm_begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeEqualTo(String value) {
            addCriterion("pm_begin_time =", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeNotEqualTo(String value) {
            addCriterion("pm_begin_time <>", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeGreaterThan(String value) {
            addCriterion("pm_begin_time >", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeGreaterThanOrEqualTo(String value) {
            addCriterion("pm_begin_time >=", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeLessThan(String value) {
            addCriterion("pm_begin_time <", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeLessThanOrEqualTo(String value) {
            addCriterion("pm_begin_time <=", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeLike(String value) {
            addCriterion("pm_begin_time like", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeNotLike(String value) {
            addCriterion("pm_begin_time not like", value, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeIn(List<String> values) {
            addCriterion("pm_begin_time in", values, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeNotIn(List<String> values) {
            addCriterion("pm_begin_time not in", values, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeBetween(String value1, String value2) {
            addCriterion("pm_begin_time between", value1, value2, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmBeginTimeNotBetween(String value1, String value2) {
            addCriterion("pm_begin_time not between", value1, value2, "pmBeginTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeIsNull() {
            addCriterion("pm_end_time is null");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeIsNotNull() {
            addCriterion("pm_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeEqualTo(String value) {
            addCriterion("pm_end_time =", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeNotEqualTo(String value) {
            addCriterion("pm_end_time <>", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeGreaterThan(String value) {
            addCriterion("pm_end_time >", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("pm_end_time >=", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeLessThan(String value) {
            addCriterion("pm_end_time <", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeLessThanOrEqualTo(String value) {
            addCriterion("pm_end_time <=", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeLike(String value) {
            addCriterion("pm_end_time like", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeNotLike(String value) {
            addCriterion("pm_end_time not like", value, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeIn(List<String> values) {
            addCriterion("pm_end_time in", values, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeNotIn(List<String> values) {
            addCriterion("pm_end_time not in", values, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeBetween(String value1, String value2) {
            addCriterion("pm_end_time between", value1, value2, "pmEndTime");
            return (Criteria) this;
        }

        public Criteria andPmEndTimeNotBetween(String value1, String value2) {
            addCriterion("pm_end_time not between", value1, value2, "pmEndTime");
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