package com.cf.service;

import com.cf.domain.Planning;
import com.cf.domain.WorkPlanning;
import com.cf.repository.PlanningRepository;
import com.cf.repository.UserRepository;
import com.cf.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Service
public class PlanningServiceImpl implements PlanningService {

    private static final Logger logger = LoggerFactory.getLogger(PlanningServiceImpl.class);

    @Inject
    private PlanningRepository planningRepository;
    @Inject
    private WorkPlanningService workPlanRepository;
    @Inject
    UserRepository userRepository;

    @Override
    public long count() {
        return planningRepository.count();
    }
    @Override
    public boolean exists(Long id) {
        return planningRepository.exists(id);
    }

    //CREATE
    @Override
    public Planning save(Planning entity) {
        logger.info("Planning.id= "+entity.getId()+" saved");
        return planningRepository.save(entity);
    }
    @Override
    public Iterable<Planning> save(Iterable<Planning> entities) {
        logger.info("Plannings: "+entities.toString()+" saved");
        return planningRepository.save(entities);
    }

    //UPDATE
    @Override
    public boolean update(Planning entity) {
        if (!entity.isNew() && this.exists(entity.getId())){
            planningRepository.save(entity);
            logger.info("Planning.id= "+entity.getId()+" updated for user.id: "+entity.getUserId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Iterable<Planning> updateAll(Iterable<Planning> entities) {
        /*List<Planning> updatedPlannings = new ArrayList<>();
        Iterator<Planning> iter = entities.iterator();
        while (iter.hasNext()){
            Planning planning = iter.next();
            if (this.update(planning)){
                updatedPlannings.add(planning);
            }
        }
        return updatedPlannings;*/
        //planningRepository.deleteAll();
        return planningRepository.save(entities);
    }

    @Override
    @Transactional
    public Iterable<Planning> applyWorkPlannings(final Long userId, final List<WorkPlanning> workPlannings, final Date startDate, final Date endDate, final Integer occurrences) {

        int maxPlannedDayOrder;
        //Get the number of weeks in WorkPlannings
        final int numberOfScheduledWeeks = countWeeksOfWorkPlannings(workPlannings);
        //Get the day order of the StartDate (Monday-1, Tuesday-2....)
        final int weekDayOfStartDate = DateUtils.getDayOfWeek(startDate);
        //Determine the maximum day order to which the WorkPlanning would be applied, used as an end criteria for the process
        if (endDate == null && occurrences > 0){

            maxPlannedDayOrder = numberOfScheduledWeeks*7*occurrences - 1;
            //endDate = DateUtils.addDays(startDate, endDayIndex);
        } else {
            maxPlannedDayOrder = DateUtils.countDaysBetweenDates(startDate, endDate);
        }
        maxPlannedDayOrder += weekDayOfStartDate;

        //startWeekDate is a base date to which we will append planned days from WorkPlannings
        //so should be from a first week day (Monday)
        final Date startWeekDate = DateUtils.addDays(startDate, -(weekDayOfStartDate-1));

        //int dayIndex = 0;
        int occurrenceIndex = 0;
        int wpIndex = 0;
        int plannedDayOrder = this.calculatePlannedDayOrder(workPlannings.get(wpIndex), numberOfScheduledWeeks, occurrenceIndex);

        List<Planning> planningList = new ArrayList<>();

        while (plannedDayOrder <= maxPlannedDayOrder){//WorkPlanning workPlanning: workPlannings) {
            //do{
            if (plannedDayOrder >= weekDayOfStartDate){
                Planning planning = new Planning(workPlannings.get(wpIndex));
                planning.setUserId(userId);//??
                planning.setDate(DateUtils.addDays(startWeekDate, plannedDayOrder-1));
                planningList.add(planning);
            }
            wpIndex++;
            if (wpIndex >= workPlannings.size()){
                wpIndex = 0;
                occurrenceIndex++;
            }
            plannedDayOrder = this.calculatePlannedDayOrder(workPlannings.get(wpIndex), numberOfScheduledWeeks, occurrenceIndex);
        } //while (plannedDay <= endDayIndex);//end while loop

        this.deleteByUserId(userId);
        this.save(planningList);

        return planningList;
    }

    private static int countWeeksOfWorkPlannings(final List<WorkPlanning> workPlannings){
        int maxWeek=1;
        for (WorkPlanning workPlan: workPlannings){
            if (workPlan.getWeek() > maxWeek) maxWeek=workPlan.getWeek();
        }
        return maxWeek;
    }

    private int calculatePlannedDayOrder(final WorkPlanning workPlanning, final int numberOfScheduledWeeks, int occurrenceIndex){

        return occurrenceIndex*numberOfScheduledWeeks*7 + (workPlanning.getWeek()-1)*7 + workPlanning.getDay();
    }

    @Override
    @Transactional
    public Iterable<Planning> applyWorkPlanningsFromDate(final Long userId, final List<WorkPlanning> workPlannings, final Date fromDate){

        Date maxDate = this.findPlanningsByUserIdWithMaxDate(userId);
        Date endDate = DateUtils.getDateOfLastWeekDay(maxDate);

        this.deleteFromDateByUserId(userId, fromDate);

        return this.applyWorkPlannings(userId, workPlannings, fromDate, endDate, null);
    }

    @Override
    @Transactional
    public void cleanWorkPlanningsFromDate(final Long userId, final Date fromDate){

        workPlanRepository.deleteByUserId(userId);

        this.deleteFromDateByUserId(userId, fromDate);
    }

    //READ
    @Override
    public Planning findById(Long id) {
        return planningRepository.findOne(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<Planning> findAll(Iterable<Long> ids) {
        return planningRepository.findAll(ids);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<Planning> findAll() {
        return planningRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsByUserId(Long userId) {
        return planningRepository.findByUserId(userId);
    }
    @Override
    @Transactional(readOnly = true)
    public Date findPlanningsByUserIdWithMaxDate(final Long userId) {
        return planningRepository.findMaxDateOfUser(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsByUserId(Long userId, Date startDate, Date endDate) {
        return planningRepository.findByUserId(userId, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsByUserId(Long userId, Date fromDate) {
        return planningRepository.findByUserId(userId, fromDate);
    }
    @Override
    @Transactional(readOnly = true)
    public int countPlanningsByUserId(Long userId, Date fromDate){
        return planningRepository.countByUserId(userId, fromDate);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsByUsername(String username) {
        return planningRepository.findByUsername(username);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsByUsername(String username, Date startDate, Date endDate) {
        return planningRepository.findByUsername(username, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsOfUserInstitution(String username){
        return planningRepository.findOfUserInstitution(username);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsOfUserInstitution(String username, Date startDate, Date endDate){
        return planningRepository.findOfUserInstitution(username, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsOfManagerSubordinates(Long managerId) {
        return planningRepository.findOfManagerSubordinatesByManagerId(managerId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsOfManagerSubordinates(Long managerId, Date startDate, Date endDate) {
        return planningRepository.findOfManagerSubordinatesByManagerId(managerId, startDate, endDate);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsOfManagerSubordinates(String managerName) {
        Long managerId = userRepository.findByUsername(managerName).getId();
        return planningRepository.findOfManagerSubordinatesByManagerId(managerId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Planning> findPlanningsOfManagerSubordinates(String managerName, Date startDate, Date endDate) {
        Long managerId = userRepository.findByUsername(managerName).getId();
        return planningRepository.findOfManagerSubordinatesByManagerId(managerId, startDate, endDate);
    }

    //DELETE
    @Override
    public void delete(Long id) {
        planningRepository.delete(id);
        logger.info("Planning.id= "+id+" deleted");
    }
    @Override
    public void delete(Planning entity) {
        planningRepository.delete(entity);
        logger.info("Planning.id= "+entity.getId()+" deleted");
    }
    @Override
    public void deleteAll() {
        planningRepository.deleteAll();
        logger.info("All plannings deleted");
    }
    @Override
    public void delete(Iterable<? extends Planning> entities) {
        planningRepository.delete(entities);
        logger.info("Plannings: "+entities.toString()+" deleted");
    }
    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        planningRepository.deleteByUserId(userId);
    }
    @Override
    @Transactional
    public void deleteFromDateByUserId(final Long userId, final Date fromDate){

        planningRepository.deleteFromDateByUserId(userId, fromDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Planning> findCorrectionsByUserId(Long userId, Date startDate, Date endDate) {
        return planningRepository.findCorrectionsByUserId(userId, startDate, endDate);
    }
}
