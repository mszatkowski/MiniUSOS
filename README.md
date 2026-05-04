# TODO
- for scheduling process: 
  - what tables do we need? 
  - what fields should they have? 
  - what do they represent? 
  - what is the relation between them?


 Proposed tables:
- Course: ..., majorId
- CourseDetail: courseDetailId, courseId, courseType, hours, majorDetailId
  - courseType (lecture, project, etc.)
  - hours (number of hours per semester)
- CourseSchedule: courseScheduleId, courseDetailId, date, startTime, endTime
- Employee: employeeId, firstname, lastname
- EmployeeCourseDetail: employeeCourseDetailId, employeeId, courseDetailId
- StudentGroup*: studentGroupId, employeeCourseDetailId, groupNumber
- StudentGroupAssignment*: studentGroupAssignmentId, studentId, studentGroupId, 
- Major: majorId, name
- MajorDetail: majorDetailId, majorId, academicYear

TODO:
* - to consider: different name

- Enrollment: courseId -> courseDetailId
- add migration for new tables
- refactor removal -> archivization

- think about how to trigger scheduling process
  - What would be the best parameters to pass? 
  - What would be the result of the scheduling?

- planned features:
  - Classroom management and search (for courseDetail scheduling)
   


# Invariants
- Employees are only lecturers
- Course is always assigned to exactly one major