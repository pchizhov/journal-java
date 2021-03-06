package ui.console;

import bl.DayTimetableManager;
import bl.entities.DatedLesson;
import bl.entities.Role;
import db.repositories.DatedLessonRepo;
import ui.Context;

import java.util.ArrayList;

public class DatedLessonPrinter {

    private Context _ctx = Context.get();

    public void print(String date) {
        ArrayList<DatedLesson> datesList = new DayTimetableManager(new DatedLessonRepo()).getList(date);
        if (datesList.size() == 0) {
            System.out.println("No lessons found for this date.");
            return;
        }
        for (DatedLesson datedLesson : datesList) {
            if (_ctx.getUser().getRole() == Role.Student &&
                    _ctx.getUser().getGroup().getNumber().equals(datedLesson.getGroup().getNumber()))
                this.printOne(datedLesson);
            else if (_ctx.getUser().getRole() == Role.Teacher &&
                    _ctx.getUser().getLogin().equals(datedLesson.getTeacher().getLogin()))
                this.printOne(datedLesson);
        }
    }

    public void printOne(DatedLesson datedLesson) {
        System.out.println("Lesson number: " + datedLesson.getNumber());
        System.out.println("Lesson name: " + datedLesson.getLesson().getName());
        System.out.println("Room number: " + datedLesson.getRoom());
        if (_ctx.getUser().getRole() == Role.Student)
            System.out.println("Teacher: " + datedLesson.getTeacher().getName());
        if (_ctx.getUser().getRole() == Role.Teacher)
            System.out.println("Group number: " + datedLesson.getGroup().getNumber());
        System.out.println();
    }

}
