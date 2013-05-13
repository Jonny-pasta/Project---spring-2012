package fi.muni.pv168.gui;

import fi.muni.pv168.backend.Ingredient;
import fi.muni.pv168.backend.IngredientManager;
import fi.muni.pv168.backend.IngredientManagerImpl;
import fi.muni.pv168.backend.MealCategory;
import fi.muni.pv168.backend.MealType;
import fi.muni.pv168.backend.Recipe;
import fi.muni.pv168.backend.RecipeManager;
import fi.muni.pv168.backend.RecipeManagerImpl;
import fi.muni.pv168.backend.Recipebook;
import fi.muni.pv168.backend.RecipebookImpl;
import fi.muni.pv168.exceptions.ServiceFailureException;
import fi.muni.pv168.utils.DBUtils;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.swing.DefaultListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingWorker;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author mimo
 */
public class RecipebookFrame extends javax.swing.JFrame {

    private boolean isSearchTextOn;
    private JComboBox searchCombo;
    private Recipebook recipebook;
    private RecipeManager recipeManager;
    private IngredientManager ingredientManager;
    private RecipeFrame recipeFrame;
    private ConfirmationFrame confirmationFrame;
    private Recipe selectedRecipe = new Recipe();
    private static final Logger logger = Logger.getLogger(RecipebookFrame.class.getName());

    private static BasicDataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:derby:memory:recipebook;create=true");
        return ds;
    }

    public RecipebookFrame() {

        confirmationFrame = new ConfirmationFrame(this);

        DataSource ds = null;
        try {
            ds = prepareDataSource();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Internal database cannot be initialized exiting", ex);
            System.exit(1);
        }

        this.recipeManager = new RecipeManagerImpl(ds);
        this.ingredientManager = new IngredientManagerImpl(ds);
        this.recipebook = new RecipebookImpl(this.ingredientManager, this.recipeManager);
        logger.log(Level.INFO, "Managers initialized");

        try {
            DBUtils.tryCreateTables(ds, Recipebook.class.getResource("/createTables.sql"));
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Tables could not be inserted into internal database, exiting", ex);
        }

        logger.log(Level.INFO, "Tables inserted into internal database, continuing happily");

        initComponents();

        isSearchTextOn = true;

        searchPanel.setLayout(new CardLayout());

        searchCombo = new JComboBox();
        searchCombo.setSize(searchText.getSize());
        searchCombo.setVisible(false);
        searchCombo.setLocation(searchText.getLocation());
        searchCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchComboActionPerformed(evt);
            }
        });

        recipeList.setFixedCellWidth(258);

        // test stuff
        Ingredient chicken = new Ingredient();
        chicken.setName("chicken");
        chicken.setAmount(1);
        chicken.setUnit("kg");
        Ingredient potatoes = new Ingredient();
        potatoes.setName("potatoes");
        potatoes.setAmount(1);
        potatoes.setUnit("kg");
        Ingredient goat = new Ingredient();
        goat.setName("goat");
        goat.setAmount(1);
        goat.setUnit("kg");
        Recipe recipe1 = new Recipe();
        recipe1.setCategory(MealCategory.MEAT);
        recipe1.setCookingTime(100);
        recipe1.setInstructions("cook chicken and potatoes");
        recipe1.setName("chicken with potatoes");
        recipe1.setNumPortions(5);
        recipe1.setType(MealType.MAIN_DISH);
        Recipe recipe2 = new Recipe();
        recipe2.setName("goat with potatoes");
        recipe2.setType(MealType.MAIN_DISH);
        recipe2.setCategory(MealCategory.MEAT);
        recipe2.setCookingTime(100);
        recipe2.setNumPortions(5);
        recipe2.setInstructions("cook chicken and potatoes");
        try {
            recipeManager.createRecipe(recipe1);
            recipeManager.createRecipe(recipe2);
            SortedSet<Ingredient> ingredients1 = new TreeSet<Ingredient>();
            ingredients1.add(chicken);
            ingredients1.add(potatoes);
            recipebook.addIngredientsToRecipe(ingredients1, recipe1);
            SortedSet<Ingredient> ingredients2 = new TreeSet<Ingredient>();
            ingredients2.add(goat);
            ingredients2.add(potatoes);
            recipebook.addIngredientsToRecipe(ingredients2, recipe2);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        // end of test stuff

        SwingWorker<SortedSet<Recipe>, Void> worker = new SwingWorker<SortedSet<Recipe>, Void>() {
            @Override
            protected SortedSet<Recipe> doInBackground() throws Exception {
                SortedSet<Recipe> recipes = new TreeSet<Recipe>();
                try {
                    recipes = recipeManager.findAllRecipes();
                    for (Recipe r : recipes) {
                        r.setIngredients(recipebook.getIngredientsOfRecipe(r));
                    }
                } catch (ServiceFailureException ex) {
                    Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                return recipes;
            }

            @Override
            protected void done() {
                try {
                    DefaultListModel listModel = new DefaultListModel();
                    SortedSet<Recipe> recipes = get();
                    for (Recipe r : recipes) {
                        listModel.addElement(r);
                    }
                    recipeList.setModel(listModel);
                    selectedRecipe = recipes.first();
                    loadRecipeToLabels(selectedRecipe);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        worker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenu7 = new javax.swing.JMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jPopupMenu4 = new javax.swing.JPopupMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenu11 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu12 = new javax.swing.JMenu();
        jMenu13 = new javax.swing.JMenu();
        jMenu14 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        recipeList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        ingredientList = new javax.swing.JList();
        label_Instructions = new javax.swing.JLabel();
        label_Ingredients = new javax.swing.JLabel();
        label_RecipeName = new javax.swing.JLabel();
        label_NumPortions = new javax.swing.JLabel();
        label_CookingTime = new javax.swing.JLabel();
        label_RecipeType = new javax.swing.JLabel();
        label_RecipeCategory = new javax.swing.JLabel();
        image = new java.awt.Canvas();
        label_Recipes = new javax.swing.JLabel();
        label_Ingredients1 = new javax.swing.JLabel();
        label_Ingredients2 = new javax.swing.JLabel();
        searchOptionsCombo = new javax.swing.JComboBox();
        searchPanel = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        addRecipeMenu = new javax.swing.JMenuItem();
        removeRecipeMenu = new javax.swing.JMenuItem();
        editRecipeMenu = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("jMenu3");

        jMenuItem2.setText("jMenuItem2");

        jMenu4.setText("jMenu4");

        jMenu7.setText("jMenu7");

        jMenu8.setText("File");
        jMenuBar2.add(jMenu8);

        jMenu9.setText("Edit");
        jMenuBar2.add(jMenu9);

        jMenu10.setText("jMenu10");

        jMenu11.setText("jMenu11");

        jMenu12.setText("File");
        jMenuBar3.add(jMenu12);

        jMenu13.setText("Edit");
        jMenuBar3.add(jMenu13);

        jMenu14.setText("jMenu14");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Recipebook");

        recipeList.setRequestFocusEnabled(false);
        recipeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recipeListClicked(evt);
            }
        });
        jScrollPane1.setViewportView(recipeList);

        ingredientList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ingredientListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(ingredientList);

        label_Instructions.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label_Instructions.setText("INSTRUCTIONS");
        label_Instructions.setToolTipText("");
        label_Instructions.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        label_Ingredients.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_Ingredients.setText("Portions:");

        label_RecipeName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_RecipeName.setText("NAME");

        label_NumPortions.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_NumPortions.setText("NP");

        label_CookingTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_CookingTime.setText("CT");

        label_RecipeType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_RecipeType.setText("TYPE");

        label_RecipeCategory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_RecipeCategory.setText("CATEGOTY");

        image.setBackground(new java.awt.Color(204, 204, 255));

        label_Recipes.setText("Recipes:");

        label_Ingredients1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Ingredients1.setText("Ingredients");

        label_Ingredients2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_Ingredients2.setText("Cooking Time:");

        searchOptionsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Show all", "Name", "Type", "Category", "Cooking time", "Ingredient" }));
        searchOptionsCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchOptionsComboActionPerformed(evt);
            }
        });

        searchPanel.setPreferredSize(new java.awt.Dimension(158, 25));

        searchText.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        searchText.setText("Search recipes by...");
        searchText.setPreferredSize(new java.awt.Dimension(158, 25));
        searchText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextMouseClicked(evt);
            }
        });
        searchText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu5.setText("File");

        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu5.add(menuExit);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Edit");

        addRecipeMenu.setText("Add Recipe");
        addRecipeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRecipeMenuActionPerformed(evt);
            }
        });
        jMenu6.add(addRecipeMenu);

        removeRecipeMenu.setText("Remove Recipe");
        removeRecipeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRecipeMenuActionPerformed(evt);
            }
        });
        jMenu6.add(removeRecipeMenu);

        editRecipeMenu.setText("Edit Recipe");
        editRecipeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRecipeMenuActionPerformed(evt);
            }
        });
        jMenu6.add(editRecipeMenu);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(searchOptionsCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(label_Recipes, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_RecipeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(label_RecipeType, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label_RecipeCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(label_Ingredients1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(42, 42, 42)
                                    .addComponent(label_Ingredients2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label_CookingTime, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label_Ingredients, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label_NumPortions, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(label_Instructions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_RecipeName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label_RecipeType, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_RecipeCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_NumPortions, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_CookingTime, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_Ingredients, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_Ingredients2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label_Ingredients1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3))
                            .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_Instructions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchOptionsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(label_Recipes, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void recipeListClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recipeListClicked
        logger.log(Level.INFO, "RecileList clicked");
        JList list = (JList) evt.getSource();
        Recipe r = (Recipe) list.getSelectedValue();
        selectedRecipe = r;
        loadRecipeToLabels(selectedRecipe);
    }//GEN-LAST:event_recipeListClicked

    private void ingredientListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ingredientListMouseClicked
        logger.log(Level.INFO, "IngredientList clicked");
    }//GEN-LAST:event_ingredientListMouseClicked

    private void addRecipeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRecipeMenuActionPerformed
        recipeFrame = new RecipeFrame(this, false, null);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                recipeFrame.setVisible(true);
            }
        });

    }//GEN-LAST:event_addRecipeMenuActionPerformed

    private void removeRecipeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRecipeMenuActionPerformed
        DefaultListModel model = (DefaultListModel) recipeList.getModel();
        if (model.size() == 1) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    WarningFrame frame = new WarningFrame();
                    frame.setText("You cannot delete last recipe!");
                    frame.setVisible(true);
                }
            });
        } else {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    confirmationFrame.setVisible(true);
                    confirmationFrame.setRecipeToDelete(selectedRecipe);
                }
            });
        }
    }//GEN-LAST:event_removeRecipeMenuActionPerformed

    private void editRecipeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRecipeMenuActionPerformed

        if (selectedRecipe != null) {
            recipeFrame = new RecipeFrame(this, true, selectedRecipe);
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    recipeFrame.setVisible(true);
                }
            });
        }
    }//GEN-LAST:event_editRecipeMenuActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed

        if (searchOptionsCombo.getSelectedIndex() == 1) {
            SwingWorker<SortedSet<Recipe>, Void> worker = new SwingWorker<SortedSet<Recipe>, Void>() {
                @Override
                protected SortedSet<Recipe> doInBackground() throws Exception {
                    SortedSet<Recipe> set = recipeManager.findRecipesByName(searchText.getText());
                    for (Recipe r : set) {
                        r.setIngredients(recipebook.getIngredientsOfRecipe(r));
                    }
                    return set;
                }

                @Override
                protected void done() {
                    try {
                        if (get().isEmpty()) {
                            WarningFrame frame = new WarningFrame();
                            frame.setText("No recipes found");
                            frame.setVisible(true);
                        } else {
                            DefaultListModel model = new DefaultListModel();
                            for (Recipe recipe : get()) {
                                model.addElement(recipe);
                            }
                            recipeList.setModel(model);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            worker.execute();
        }

        if (searchOptionsCombo.getSelectedIndex() == 4) {
            SwingWorker<SortedSet<Recipe>, Void> worker = new SwingWorker<SortedSet<Recipe>, Void>() {
                @Override
                protected SortedSet<Recipe> doInBackground() throws Exception {
                    String[] lines = searchText.getText().split("-");
                    int fromTime;
                    int toTime;
                    if (lines.length != 2) {
                        return null;
                    }
                    try {
                        fromTime = Integer.parseInt(lines[0]);
                        toTime = Integer.parseInt(lines[1]);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                    SortedSet<Recipe> set = recipeManager.findRecipesByCookingTime(fromTime, toTime);
                    for (Recipe r : set) {
                        r.setIngredients(recipebook.getIngredientsOfRecipe(r));
                    }
                    return set;
                }

                @Override
                protected void done() {
                    try {
                        if (get() == null) {
                            WarningFrame frame = new WarningFrame();
                            frame.setText("Wrong format of cooking time search!");
                        } else {
                            if (get().isEmpty()) {
                                WarningFrame frame = new WarningFrame();
                                frame.setText("No recipes found");
                                frame.setVisible(true);
                            } else {
                                DefaultListModel model = new DefaultListModel();
                                for (Recipe recipe : get()) {
                                    model.addElement(recipe);
                                }
                                recipeList.setModel(model);
                            }
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            worker.execute();
        }

        if (searchOptionsCombo.getSelectedIndex() == 5) {
            SwingWorker<SortedSet<Recipe>, Void> worker = new SwingWorker<SortedSet<Recipe>, Void>() {
                @Override
                protected SortedSet<Recipe> doInBackground() throws Exception {
                    SortedSet<Recipe> set = recipebook.findRecipesByIngredientName(searchText.getText());
                    for (Recipe r : set) {
                        r.setIngredients(recipebook.getIngredientsOfRecipe(r));
                    }
                    return set;
                }

                @Override
                protected void done() {
                    try {
                        if (get().isEmpty()) {
                            WarningFrame frame = new WarningFrame();
                            frame.setText("No recipes found");
                            frame.setVisible(true);
                        } else {
                            DefaultListModel model = new DefaultListModel();
                            for (Recipe recipe : get()) {
                                model.addElement(recipe);
                            }
                            recipeList.setModel(model);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            worker.execute();
        }
    }//GEN-LAST:event_searchTextActionPerformed

    private void searchTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextMouseClicked
        searchText.setText("");
    }//GEN-LAST:event_searchTextMouseClicked

    private void searchOptionsComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchOptionsComboActionPerformed
        if (searchOptionsCombo.getSelectedIndex() == 0) {
            if (!isSearchTextOn) {
                searchPanel.remove(searchCombo);
                searchPanel.add(searchText);
                searchCombo.setVisible(false);
                searchText.setVisible(true);
                isSearchTextOn = true;
            }
            searchText.setText("Search recipes by...");
            SwingWorker<SortedSet<Recipe>, Void> worker = new SwingWorker<SortedSet<Recipe>, Void>() {
                @Override
                protected SortedSet<Recipe> doInBackground() throws Exception {
                    SortedSet<Recipe> set = recipeManager.findAllRecipes();
                    for (Recipe r : set) {
                        r.setIngredients(recipebook.getIngredientsOfRecipe(r));
                    }
                    return set;
                }

                @Override
                protected void done() {
                    try {
                        if (get().isEmpty()) {
                            WarningFrame frame = new WarningFrame();
                            frame.setText("No recipes found");
                            frame.setVisible(true);
                        } else {
                            DefaultListModel model = new DefaultListModel();
                            for (Recipe recipe : get()) {
                                model.addElement(recipe);
                            }
                            recipeList.setModel(model);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            worker.execute();
        }

        if (searchOptionsCombo.getSelectedIndex() == 1) {
            if (!isSearchTextOn) {
                searchPanel.remove(searchCombo);
                searchPanel.add(searchText);
                searchCombo.setVisible(false);
                searchText.setVisible(true);
                isSearchTextOn = true;
            }
            searchText.setText("Enter recipe's name...");
        }
        if (searchOptionsCombo.getSelectedIndex() == 2) {
            if (isSearchTextOn) {
                searchPanel.remove(searchText);
                searchPanel.add(searchCombo);
                searchText.setVisible(false);
                searchCombo.setVisible(true);
                isSearchTextOn = false;
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            model.addElement("Breakfast");
            model.addElement("Appetizer");
            model.addElement("Soup");
            model.addElement("Main dish");
            model.addElement("Salad");
            model.addElement("Dessert");
            model.addElement("Drink");
            searchCombo.setModel(model);
        }
        if (searchOptionsCombo.getSelectedIndex() == 3) {
            if (isSearchTextOn) {
                searchPanel.remove(searchText);
                searchPanel.add(searchCombo);
                searchText.setVisible(false);
                searchCombo.setVisible(true);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            model.addElement("Meat");
            model.addElement("Meatless");
            model.addElement("Fish");
            model.addElement("Sweet");
            model.addElement("Alcoholic");
            model.addElement("Non-alcoholic");
            model.addElement("Pasta");
            searchCombo.setModel(model);
        }
        if (searchOptionsCombo.getSelectedIndex() == 4) {
            if (!isSearchTextOn) {
                searchPanel.remove(searchCombo);
                searchPanel.add(searchText);
                searchCombo.setVisible(false);
                searchText.setVisible(true);
                isSearchTextOn = true;
            }
            searchText.setText("Enter interval: X-Y...");
        }
        if (searchOptionsCombo.getSelectedIndex() == 5) {
            if (!isSearchTextOn) {
                searchPanel.remove(searchCombo);
                searchPanel.add(searchText);
                searchCombo.setVisible(false);
                searchText.setVisible(true);
                isSearchTextOn = true;
            }
            searchText.setText("Enter ingredient's name...");
        }
    }//GEN-LAST:event_searchOptionsComboActionPerformed

    private void searchComboActionPerformed(java.awt.event.ActionEvent evt) {
        if (searchOptionsCombo.getSelectedIndex() == 2) {
            SwingWorker<SortedSet<Recipe>, Void> worker = new SwingWorker<SortedSet<Recipe>, Void>() {
                @Override
                protected SortedSet<Recipe> doInBackground() throws Exception {
                    SortedSet<Recipe> set = recipeManager.findRecipesByType(MealType.fromInt(searchCombo.getSelectedIndex()));
                    for (Recipe r : set) {
                        r.setIngredients(recipebook.getIngredientsOfRecipe(r));
                    }
                    return set;
                }

                @Override
                protected void done() {
                    try {
                        if (get().isEmpty()) {
                            WarningFrame frame = new WarningFrame();
                            frame.setText("No recipes found");
                            frame.setVisible(true);
                        } else {
                            DefaultListModel model = new DefaultListModel();
                            for (Recipe recipe : get()) {
                                model.addElement(recipe);
                            }
                            recipeList.setModel(model);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            worker.execute();
        }

        if (searchOptionsCombo.getSelectedIndex() == 3) {
            SwingWorker<SortedSet<Recipe>, Void> worker = new SwingWorker<SortedSet<Recipe>, Void>() {
                @Override
                protected SortedSet<Recipe> doInBackground() throws Exception {
                    SortedSet<Recipe> set = recipeManager.findRecipesByCategory(MealCategory.fromInt(searchCombo.getSelectedIndex()));
                    for (Recipe r : set) {
                        r.setIngredients(recipebook.getIngredientsOfRecipe(r));
                    }
                    return set;
                }

                @Override
                protected void done() {
                    try {
                        if (get().isEmpty()) {
                            WarningFrame frame = new WarningFrame();
                            frame.setText("No recipes found");
                            frame.setVisible(true);
                        } else {
                            DefaultListModel model = new DefaultListModel();
                            for (Recipe recipe : get()) {
                                model.addElement(recipe);
                            }
                            recipeList.setModel(model);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(RecipebookFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            worker.execute();
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;


                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RecipebookFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RecipebookFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RecipebookFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RecipebookFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RecipebookFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addRecipeMenu;
    private javax.swing.JMenuItem editRecipeMenu;
    private java.awt.Canvas image;
    private javax.swing.JList ingredientList;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel label_CookingTime;
    private javax.swing.JLabel label_Ingredients;
    private javax.swing.JLabel label_Ingredients1;
    private javax.swing.JLabel label_Ingredients2;
    private javax.swing.JLabel label_Instructions;
    private javax.swing.JLabel label_NumPortions;
    private javax.swing.JLabel label_RecipeCategory;
    private javax.swing.JLabel label_RecipeName;
    private javax.swing.JLabel label_RecipeType;
    private javax.swing.JLabel label_Recipes;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JList recipeList;
    private javax.swing.JMenuItem removeRecipeMenu;
    private javax.swing.JComboBox searchOptionsCombo;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchText;
    // End of variables declaration//GEN-END:variables

    private void loadRecipeToLabels(Recipe recipe) {
        label_RecipeName.setText(recipe.getName());
        label_RecipeCategory.setText(recipe.getCategory().toString());
        label_RecipeType.setText(recipe.getType().toString());
        label_CookingTime.setText(new Integer(recipe.getCookingTime()).toString());
        label_NumPortions.setText(new Integer(recipe.getNumPortions()).toString());
        label_Instructions.setText(recipe.getInstructions());
        DefaultListModel model = new DefaultListModel();
        for (Ingredient ingredient : recipe.getIngredients()) {
            model.addElement(ingredient);
        }
        ingredientList.setModel(model);
    }

    public void updateRecipeList(Recipe recipe, boolean b) {
        DefaultListModel model = (DefaultListModel) recipeList.getModel();
        if (b) {
            model.addElement(recipe);
        } else {
            if (model.indexOf(recipe) + 1 < model.size()) {
                selectedRecipe = (Recipe) model.get(model.indexOf(recipe) + 1);
                loadRecipeToLabels(selectedRecipe);
            } else {
                if (model.indexOf(recipe) + 1 == model.size()) {
                    selectedRecipe = (Recipe) model.get(model.indexOf(recipe) - 1);
                    loadRecipeToLabels(selectedRecipe);
                }
            }
            model.removeElement(recipe);
        }
        recipeList.setModel(model);
    }

    public void createRecipe(final Recipe recipe, final SortedSet<Ingredient> ingrs) {

        SwingWorker<Recipe, Void> worker = new SwingWorker<Recipe, Void>() {
            @Override
            protected Recipe doInBackground() throws Exception {
                try {
                    SortedSet<Ingredient> is = new TreeSet<Ingredient>(ingrs);
                    Recipe r = new Recipe();

                    r.setName(recipe.getName());
                    r.setType(recipe.getType());
                    r.setNumPortions(recipe.getNumPortions());
                    r.setCategory(recipe.getCategory());
                    r.setCookingTime(recipe.getCookingTime());
                    r.setInstructions(recipe.getInstructions());

                    recipeManager.createRecipe(r);
                    recipebook.addIngredientsToRecipe(is, r);
                    return r;
                } catch (ServiceFailureException e) {
                    logger.log(Level.SEVERE, "adding recipe t DB failed", e);
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    updateRecipeList(get(), true);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "get failed with exception", e);
                }
            }
        };
        worker.execute();
    }

    public void deleteRecipe(final Recipe recipe) {


        SwingWorker<Recipe, Void> worker = new SwingWorker<Recipe, Void>() {
            @Override
            protected Recipe doInBackground() throws Exception {
                try {
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        ingredientManager.deleteIngredient(ingredient, recipe.getId());
                    }
                    recipeManager.deleteRecipe(recipe);
                } catch (ServiceFailureException ex) {
                    logger.log(Level.SEVERE, "error while deleting recipe from DB", recipe);
                }
                return recipe;
            }

            @Override
            protected void done() {
                try {
                    updateRecipeList(recipe, false);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "get failed with exception", e);
                }
            }
        };
        worker.execute();
    }

    public void updateRecipe(final Recipe recipe, final SortedSet<Ingredient> toAdd, final SortedSet<Ingredient> toUpdate) {

        SwingWorker<Recipe, Void> worker = new SwingWorker<Recipe, Void>() {
            @Override
            protected Recipe doInBackground() throws Exception {
                try {
                    SortedSet<Ingredient> is = new TreeSet<Ingredient>(toAdd);
                    Recipe r = new Recipe();

                    r.setId(recipe.getId());
                    r.setName(recipe.getName());
                    r.setType(recipe.getType());
                    r.setNumPortions(recipe.getNumPortions());
                    r.setCategory(recipe.getCategory());
                    r.setCookingTime(recipe.getCookingTime());
                    r.setInstructions(recipe.getInstructions());

                    r.setIngredients(new TreeSet<Ingredient>());

                    for (Ingredient ingredient : toUpdate) {
                        ingredientManager.updateIngredient(ingredient);
                        r.addIngredient(ingredient);
                    }

                    recipeManager.updateRecipe(r);
                    recipebook.addIngredientsToRecipe(is, r);
                    return r;
                } catch (ServiceFailureException e) {
                    logger.log(Level.SEVERE, "adding recipe to DB failed", e);
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    updateRecipeList(get(), true);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "get failed with exception", e);
                }
            }
        };
        worker.execute();
    }
}