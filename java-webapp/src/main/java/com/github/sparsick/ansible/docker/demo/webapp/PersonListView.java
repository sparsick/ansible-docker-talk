package com.github.sparsick.ansible.docker.demo.webapp;

import com.github.sparsick.ansible.docker.demo.webapp.domain.Person;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

final class PersonListView extends ListView<Person> {

    /**  */
    private static final long serialVersionUID = 9172837350405031400L;


    PersonListView(String id, List<Person> list) {
        super(id, list);
    }


    @Override
    protected void populateItem(ListItem<Person> item) {
      Person person = item.getModelObject();
      item.add(new Label("firstname", person.getFirstName()));
      item.add(new Label("lastname", person.getLastName()));
      item.add(new Label("jobtitle", person.getJobTitle()));
    }
}