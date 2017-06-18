package ru.xidv.drankov.fassist.dm.dao;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xidv.drankov.fassist.ser.category_jsons.CategoryJSON;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Category")
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CAT_ID")
    @Getter
    private Long id;

    @Getter
    private Integer type;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @OneToMany
    @JoinColumn(name = "parent_id")
    @Getter
    @Setter
    private Set<Category> subcategories = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    @Getter
    @Setter
    private Set<Operation> operations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @Getter
    private Category parent;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Getter
    private User user;

    public Category(Integer type, String name, Category parent, User user) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        this.user = user;
        if (parent != null) {
            parent.getSubcategories().add(this);
        }
    }

    public CategoryJSON get_as_json() {
        List<Long> collection_of_id = getSubcategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        double sum = rec_sum(this);

        return new CategoryJSON(id,
                name,
                description,
                type,
                collection_of_id,
                sum
        );
    }

    private static double rec_sum(Category category) {
        double sum = 0d;
        sum += category.getOperations()
                .stream()
                .map(Operation::getSum)
                .reduce((x, y) -> x + y)
                .orElse(0d);

        for (Category c : category.getSubcategories()) {
            sum += rec_sum(c);
        }

        return sum;
    }
}
