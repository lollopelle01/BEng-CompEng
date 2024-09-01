package it.unibo.paw.dao;


import java.util.Calendar;
import java.util.List;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	public static void main(String[] args) {
		
		// Creo istanza DAO
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		
		// Creo database
		StudentDAO studentDAO = daoFactoryInstance.getStudentDAO();
		studentDAO.dropTable();
		studentDAO.createTable();
		
		// Creo i DTO e li inserico
		StudentDTO s = new StudentDTO();
		Calendar c = Calendar.getInstance();
		c.set(1984, 1, 24);
		s.setId(1);
		s.setFirstName("Luisa");
		s.setLastName("Verdi");
		s.setBirthDate(c.getTime());
		studentDAO.create(s);

		s = new StudentDTO();
		c.set(1985, 4, 2);
		s.setId(2);
		s.setFirstName("Anna");
		s.setLastName("Bruni");
		java.util.Date d = c.getTime(); 
		s.setBirthDate(d);
		studentDAO.create(s);
		
		// Creo altro Database
		CourseDAO courseDAO = daoFactoryInstance.getCourseDAO();
		courseDAO.dropTable();
		courseDAO.createTable();
		
		// Creo altri DTO e li inserisco
		CourseDTO course = new CourseDTO();
		course.setId(1);
		course.setName("Progettazione di Applicazioni Web");
		courseDAO.create(course);

		course = new CourseDTO();
		course.setId(2);
		course.setName("Fondamenti di Informatica T1");
		courseDAO.create(course);
				
		
		// Creo altro database (mapping)
		CourseStudentMappingDAO mappingDAO = daoFactoryInstance.getStudentCourseMappingDAO();
		mappingDAO.dropTable();
		mappingDAO.createTable();

		// Compilo tabella
		mappingDAO.create(1, 1);
		mappingDAO.create(1, 2);
		mappingDAO.create(2, 2);
		
		// Faccio richieste ed analizzo i risultati
		StudentDTO student = studentDAO.read(1);
		System.out.println(student.getFirstName()+" "+student.getLastName()+" frequenta i seguenti corsi:");
		List<CourseDTO> corsiDiStudent = student.getCourses();
		for(CourseDTO cds : corsiDiStudent)
		{
			System.out.println(""+cds.getId()+" "+cds.getName());
		}
		System.out.println();
		
		CourseDTO paw = courseDAO.read(1);
		System.out.println("Il corso "+paw.getName()+" � frequentato da:");
		List<StudentDTO> sdc = paw.getStudents();
		for(StudentDTO stu : sdc){
			
			System.out.println(""+stu.getFirstName()+" "+stu.getLastName()+" "+stu.getBirthDate());;
		}
		System.out.println();
		System.out.println();
	}

}
