package org.admin.repository;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalReportDao {
    public List<Report> findAll();

    public Report findBy(int rentalNo, int memberNo);

}
