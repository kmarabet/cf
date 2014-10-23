package com.cf.service;

import com.cf.domain.EmployeeType;
import com.cf.domain.Institution;
import com.cf.domain.Registration;
import com.cf.domain.User;
import com.cf.domain.WorkException;
import com.cf.domain.WorkPlanning;
import com.cf.repository.EmployeeTypeRepository;
import com.cf.repository.InstitutionRepository;
import com.cf.repository.RegistrationRepository;
import com.cf.repository.UserRepository;
import com.cf.repository.WorkPlanningRepository;
import com.cf.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;


@Service
public class DbInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PlanningServiceImpl.class);

    static String usersInputFile = "WEB-INF\\data\\users.csv";
    static String institutionsInputFile = "WEB-INF\\data\\institutions.csv";
    static String registrationsInputFile = "WEB-INF\\data\\registrations.csv";
    static String workPlanningInputFile = "WEB-INF\\data\\work-plannings.csv";

    private final static Character CSV_DELIMITER = ',';

    @Inject
    private InstitutionRepository institutionRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private RegistrationRepository registerRepository;
    @Inject
    private WorkPlanningRepository workPlanRepository;
    @Inject
    private EmployeeTypeRepository employeeTypeRepository;

    //@Resource(name = "passwordEncoder")
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private Environment environment;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {//Only for root context
            init(event.getApplicationContext());//
        }
    }

    @Transactional
    public void init(ApplicationContext applicationContext) {
        try {
            this.initHardCodedData();
            //"dev" profile is set (activated) in web.xml - spring.profiles.default
            if (environment.acceptsProfiles("dev", "qa")) {
                logger.info("DEV or QA set properly");
                //this.initTestDevDataForUsersAndRegistrations(applicationContext);
            } else {
                logger.error("DEV or QA not set");
            }
        } catch (/*IOException |*/ ParseException e) {
            e.printStackTrace();
        }
    }

    private void initHardCodedData() throws ParseException {
        //todo: add initial and mandatory data if required
        initEmployeeType();
    }

    private void initTestDevDataForUsersAndRegistrations(ApplicationContext applicationContext) throws IOException, ParseException {

        String text = null;

        //Populate Institution's test data from institution.csv
        File inputFile = applicationContext.getResource(institutionsInputFile).getFile();
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        //Map<Long, Institution> institutionMap = new HashMap<>();
        int rowIndex = 0;
        while ((text = br.readLine()) != null) {

            String splitRow[] = text.split(CSV_DELIMITER.toString());

            if (rowIndex != 0) {
                Institution institution = new Institution();
                institution.setId(Long.valueOf(splitRow[0]));
                institution.setName(splitRow[1]);
                institution.setUrl(splitRow[2]);
                institution.setTypes(splitRow[3]);

                //institutionMap.put(institution.getId(), institution);

                institutionRepository.save(institution);
            }
            rowIndex++;
        }

        //Populate Users' test data from users.csv
        inputFile = applicationContext.getResource(usersInputFile).getFile();
        br = new BufferedReader(new FileReader(inputFile));
        rowIndex = 0;
        while ((text = br.readLine()) != null) {

            String splitRow[] = text.split(CSV_DELIMITER.toString());

            if (rowIndex != 0) {
                User user = new User();
                user.setId(Long.valueOf(splitRow[0]));
                user.setUsername(splitRow[1]);
                user.setFirstName(splitRow[2]);
                user.setLastName(splitRow[3]);
                user.setUsertypeId(Long.valueOf(splitRow[4]));
                user.setUsertypeName(splitRow[5]);
                user.setPin(passwordEncoder.encode(splitRow[6]));
                user.setManagerId("null".equals(splitRow[7]) ? null : Long.valueOf(splitRow[7]));
                user.setInstitutionId(Long.valueOf(splitRow[8]));

                userRepository.save(user);
            }
            rowIndex++;
        }

        //Populate WorkPlannings' test data from work-plannings.csv
        inputFile = applicationContext.getResource(workPlanningInputFile).getFile();
        br = new BufferedReader(new FileReader(inputFile));
        rowIndex = 0;
        while ((text = br.readLine()) != null) {

            String splitRow[] = text.split(CSV_DELIMITER.toString());

            if (rowIndex != 0) {
                WorkPlanning workPlan = new WorkPlanning();
                workPlan.setId(Long.valueOf(rowIndex - 1));
                workPlan.setUserId(Long.valueOf(splitRow[0]));
                workPlan.setWeek(Integer.valueOf(splitRow[1]));
                workPlan.setDay(Integer.valueOf(splitRow[2]));
                workPlan.setTimeIn(DateUtils.parseTime(splitRow[3]));
                workPlan.setTimeOut(DateUtils.parseTime(splitRow[4]));

                workPlanRepository.save(workPlan);
            }
            rowIndex++;
        }

        //Populate Registrations' test data from registrations.csv
        inputFile = applicationContext.getResource(registrationsInputFile).getFile();
        br = new BufferedReader(new FileReader(inputFile));
        rowIndex = 0;
        while ((text = br.readLine()) != null) {

            String splitRow[] = text.split(CSV_DELIMITER.toString());

            if (rowIndex != 0) {
                Registration register = new Registration();
                register.setId(Long.valueOf(rowIndex));
                register.setUserId(Long.valueOf(splitRow[0]));
                register.setDate(DateUtils.parseDateTestData(splitRow[1]));
                //register.setDate(dateFormat.parse(splitRow[1]));
                register.setTimeIn(DateUtils.parseTime(splitRow[2]));
                //register.setTimeIn(new Time(timeFormat.parse(splitRow[2]).getTime()));
                register.setTimeOut(DateUtils.parseTime(splitRow[3]));

                registerRepository.save(register);
            }
            rowIndex++;
        }

    }

    private void initEmployeeType() throws ParseException {

        WorkException ilness1 = new WorkException();
        ilness1.setId(1l);
        ilness1.setName("Ilness1");
        ilness1.setStartTime(DateUtils.parseDateTime("25-02-2013 09:10:11", "dd-MM-yyyy HH:mm:ss"));
        ilness1.setEndTime(DateUtils.parseDateTime("25-02-2013 22:10:11", "dd-MM-yyyy HH:mm:ss"));

        WorkException ilness2 = new WorkException();
        ilness2.setId(2l);
        ilness2.setName("Ilness1");
        ilness2.setStartTime(DateUtils.parseDateTime("25-02-2014 09:10:11", "dd-MM-yyyy HH:mm:ss"));
        ilness2.setEndTime(DateUtils.parseDateTime("25-02-2014 22:10:11", "dd-MM-yyyy HH:mm:ss"));

        WorkException ilness3 = new WorkException();
        ilness3.setId(3l);
        ilness3.setName("Ilness3");
        ilness3.setStartTime(DateUtils.parseDateTime("25-02-2014 09:10:11", "dd-MM-yyyy HH:mm:ss"));
        ilness3.setEndTime(DateUtils.parseDateTime("25-02-2014 22:10:11", "dd-MM-yyyy HH:mm:ss"));

        EmployeeType fullTimeEmployee = new EmployeeType();
        fullTimeEmployee.setId(1l);
        fullTimeEmployee.setName("Full-time");
        fullTimeEmployee.setWeeklyWorkHours(40);
        fullTimeEmployee.addWorkException(ilness1);
        fullTimeEmployee.addWorkException(ilness2);
        fullTimeEmployee.addWorkException(ilness3);

        employeeTypeRepository.save(fullTimeEmployee);

        EmployeeType partTimeEmployee = new EmployeeType();
        partTimeEmployee.setId(2l);
        partTimeEmployee.setName("Part-time");
        partTimeEmployee.setWeeklyWorkHours(40);

        //partTimeEmployee.setWorkExceptions(null);

        employeeTypeRepository.save(partTimeEmployee);

    }
}
