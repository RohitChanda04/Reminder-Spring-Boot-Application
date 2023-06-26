# Reminder-Spring-Boot-Application
A Reminder web application built on Spring Boot to demonstrate the MVC architecture and functionalities.

Developed using multiple technologies, the "***Reminder Application***" is a web-based application that provides a user-friendly interface for managing reminders, allowing users to create, update, and delete reminders. It also offers features such as sorting reminders, selecting and deleting multiple reminders, and viewing expired and expiring reminders. Implenting and integrating Spring Security and Profiles would have been difficult for beginners to comprehend hence, I have simply used basic validations for users and created profiles on the basis of combinations. Also, as far as sessions are considered, this web application is a demo to the Spring MVC architecture and functionalities so, the reader is advised to take the simplicity into consideration while running it.


### Technologies used :- 

•	**Java** – used as the backend language

•	**Spring Boot** – the framework used to integrate everything

•	**Thymeleaf** – used to dynamically bind the data to the model

•	**MySQL** – the database used

•	**HTML** – used to write the frontend pages

•	**CSS** – used to style the pages

•	**JavasScript** – to implement the client-side validations and sorting to display accordingly

•	**Bootstrap 4** – used to inherit and integrate the predesigned templates for styling

<br>

## Features :-

•	***Login and Sign up*** - Users can create accounts using just their name and password if they are already not registered. If one is a returning user, then they can simply login using their credentials.

•	***Admin preferences*** - The admin can see the reminders of all the users while the users can only see the ones that have been set by them.

•	***Reminder Management*** - Users can create new reminders by providing details such as owner information, priority level, deadline, and reminder text. Existing reminders can be updated with new information, and reminders can be deleted individually or in bulk.

•	***Expiration Tracking*** - The application automatically identifies reminders that have passed their deadlines and provides a separate view to display expired reminders.

•	***Expiring Reminders*** - Users can easily view reminders that are expiring on the current day, helping them stay informed about upcoming deadlines.

•	***Sorting*** - Reminders can be sorted based on various criteria, including earliest deadline, latest deadline, increasing priority, decreasing priority, and owner. This allows users to organize their reminders according to their preferences. The "Reset to Default" option is also available to restore the original sorting order.

•	***Navigation*** - The application features a navigation bar at the top of each page, providing easy access to essential sections. Users can quickly navigate to the main page displaying all reminders, the page displaying expired reminders, and the page displaying reminders expiring today. In addition, the navigation bar includes links to the developer's GitHub, LinkedIn, Spotify, Apple Music, and YouTube profiles, allowing users to explore their work.

•	***Responsive Design*** - The user interface is designed using Bootstrap 4 and CSS, ensuring a responsive layout that adapts to different screen sizes and devices. This enables users to access and use the application seamlessly on various devices, including desktops, laptops, tablets, and mobile devices.

<br>

## Prerequisites :-

Before running the Reminder Application, ensure that you have the following software installed:
•	Java Development Kit (JDK) 8 or higher
•	MySQL database server
•	SpringToolSuite4 or an IDE of your choice

<br>

## Getting started :-

To set up and run the Reminder Application on your local machine, follow these steps:
1.	Clone the repository to your local machine:

> git clone https://github.com/RohitChanda04/Reminder-Spring-Boot-Application.git

2.	Open the project in your preferred IDE (e.g., SpringToolSuite4, IntelliJ IDEA, Eclipse).

3.	Configure the MySQL database connection in the application.properties file. Update the following properties with your database credentials:

> spring.datasource.url=jdbc:mysql://localhost:3306/<database-name>

> spring.datasource.username="your username goes here"

> spring.datasource.password="your password goes here"

4.	Build and run the application from your IDE or use the following Maven command in the project's root directory:
> mvn spring-boot:run

5.	Access the Reminder Application in your web browser using the following URL:
> http://localhost:8080/reminders 

6.	You should now see the list of reminders and be able to use the various features of the application.

<br>

## Usage :-

The Reminder Application offers the following functionality:

•	***Adding a Reminder*** - To create a new reminder, click on the "Add Reminder" button. You will be directed to the "create_reminder.html" page, where you can enter the necessary details, such as the owner, priority, deadline, and reminder text. Clicking the "Submit" button will add the reminder to the list, and you will be redirected back to the main "reminders.html" page.

•	***Updating a Reminder*** - If you want to modify an existing reminder, click the "Update" button corresponding to that reminder in the list. This action will take you to the "edit_reminder.html" page, where you can edit the desired fields. After making the necessary changes, click the "Submit" button to save the updated information. You will then be redirected back to the main page, where the changes will be reflected.

•	***Deleting a Reminder*** - To delete a reminder, click the "Delete" button associated with that reminder in the list. The reminder will be permanently removed from the system.

•	***Selecting and Deleting Multiple Reminders*** - You can select multiple reminders for deletion by checking the checkboxes provided at the end of each row. After selecting the desired reminders, click the "Delete Selected" button to delete them together.

•	***Deleting All Reminders*** - If you wish to delete all reminders at once, click the "Delete All" button. This action will remove all reminders from the system.

•	***Sorting Reminders*** - To sort the reminders based on different criteria, click the "Sort By" button. A dropdown menu will appear, allowing you to choose from options such as –

o	*Earliest Deadline*

o	*Latest Deadline*

o	*Increasing Priority*

o	*Decreasing Priority*

o	*Owner*

o	*Reset To Default*

Selecting an option will reorder the reminders accordingly.

•	***Viewing Expired and Expiring Reminders*** - Use the navigation bar to access the "Expired" and "Expiring" pages. The "Expired" page displays all reminders that have passed their deadlines, while the "Expiring" page shows reminders that are expiring today.

<br>

## Developer’s note :-

For the sake of simplicity and comprehension, I have not implemented Spring security anf Spring profiles in this project. The creation of accounts and fetching them from the database is done simply on the basis of the primary key and the related fields. This is done deliberately to keep the project as light as possible so that the viewer is able to grasp as much as possible. I will be implementing the required dependencies to secure the application in future.

<br>

## Contributing :-
Contributions to the Reminder Application are welcome. If you have any ideas, improvements, or bug fixes, please follow these steps:

1.	Fork the repository.

2.	Create a new branch for your feature/fix:

> git checkout -b feature/your-feature-name

3.	Make your changes and commit them:

> git commit -m "Add your commit message here"

4.	Push the changes to your forked repository:
> git push origin feature/your-feature-name

6.	Open a pull request on the main repository, explaining your changes and their purpose.

