package com.nyatetduwit;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.nyatetduwit.core.di.DataStoreModule_ProvideDataStoreFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideAccountDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideBudgetDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideCategoryDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideDatabaseFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideDebtDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideExportManagerFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideGoalDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideRecurringTransactionDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideTemplateDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideTransactionDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideTransactionSplitDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideTransactionTagDaoFactory;
import com.nyatetduwit.core.worker.AutoBackupWorker;
import com.nyatetduwit.core.worker.AutoBackupWorker_AssistedFactory;
import com.nyatetduwit.core.worker.RecurringTransactionWorker;
import com.nyatetduwit.core.worker.RecurringTransactionWorker_AssistedFactory;
import com.nyatetduwit.core.worker.ReminderScheduler;
import com.nyatetduwit.core.worker.ReminderWorker;
import com.nyatetduwit.core.worker.ReminderWorker_AssistedFactory;
import com.nyatetduwit.data.local.ExportManager;
import com.nyatetduwit.data.local.NyatetDuwitDatabase;
import com.nyatetduwit.data.local.PdfExportManager;
import com.nyatetduwit.data.local.dao.AccountDao;
import com.nyatetduwit.data.local.dao.BudgetDao;
import com.nyatetduwit.data.local.dao.CategoryDao;
import com.nyatetduwit.data.local.dao.DebtDao;
import com.nyatetduwit.data.local.dao.GoalDao;
import com.nyatetduwit.data.local.dao.RecurringTransactionDao;
import com.nyatetduwit.data.local.dao.TemplateDao;
import com.nyatetduwit.data.local.dao.TransactionDao;
import com.nyatetduwit.data.local.dao.TransactionSplitDao;
import com.nyatetduwit.data.local.dao.TransactionTagDao;
import com.nyatetduwit.data.repository.AccountRepositoryImpl;
import com.nyatetduwit.data.repository.BudgetRepositoryImpl;
import com.nyatetduwit.data.repository.CategoryRepositoryImpl;
import com.nyatetduwit.data.repository.DebtRepositoryImpl;
import com.nyatetduwit.data.repository.GoalRepositoryImpl;
import com.nyatetduwit.data.repository.RecurringTransactionRepositoryImpl;
import com.nyatetduwit.data.repository.SettingsRepositoryImpl;
import com.nyatetduwit.data.repository.TemplateRepositoryImpl;
import com.nyatetduwit.data.repository.TransactionRepositoryImpl;
import com.nyatetduwit.data.repository.TransactionSplitRepositoryImpl;
import com.nyatetduwit.data.repository.TransactionTagRepositoryImpl;
import com.nyatetduwit.domain.repository.AccountRepository;
import com.nyatetduwit.domain.repository.BudgetRepository;
import com.nyatetduwit.domain.repository.CategoryRepository;
import com.nyatetduwit.domain.repository.DebtRepository;
import com.nyatetduwit.domain.repository.GoalRepository;
import com.nyatetduwit.domain.repository.RecurringTransactionRepository;
import com.nyatetduwit.domain.repository.SettingsRepository;
import com.nyatetduwit.domain.repository.TemplateRepository;
import com.nyatetduwit.domain.repository.TransactionRepository;
import com.nyatetduwit.domain.repository.TransactionSplitRepository;
import com.nyatetduwit.domain.repository.TransactionTagRepository;
import com.nyatetduwit.domain.usecase.account.AddAccountUseCase;
import com.nyatetduwit.domain.usecase.account.CheckAccountTransactionsUseCase;
import com.nyatetduwit.domain.usecase.account.DeleteAccountUseCase;
import com.nyatetduwit.domain.usecase.account.GetAccountByIdUseCase;
import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase;
import com.nyatetduwit.domain.usecase.account.GetTotalBalanceUseCase;
import com.nyatetduwit.domain.usecase.account.UpdateAccountUseCase;
import com.nyatetduwit.domain.usecase.budget.AddBudgetUseCase;
import com.nyatetduwit.domain.usecase.budget.DeactivateBudgetUseCase;
import com.nyatetduwit.domain.usecase.budget.DeleteBudgetUseCase;
import com.nyatetduwit.domain.usecase.budget.GetBudgetsUseCase;
import com.nyatetduwit.domain.usecase.budget.UpdateBudgetUseCase;
import com.nyatetduwit.domain.usecase.category.AddCategoryUseCase;
import com.nyatetduwit.domain.usecase.category.DeleteCategoryUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoriesByTypeUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoryByIdUseCase;
import com.nyatetduwit.domain.usecase.category.SeedDefaultCategoriesUseCase;
import com.nyatetduwit.domain.usecase.category.UpdateCategoryUseCase;
import com.nyatetduwit.domain.usecase.debt.AddDebtPaymentUseCase;
import com.nyatetduwit.domain.usecase.debt.AddDebtUseCase;
import com.nyatetduwit.domain.usecase.debt.DeleteDebtPaymentUseCase;
import com.nyatetduwit.domain.usecase.debt.DeleteDebtUseCase;
import com.nyatetduwit.domain.usecase.debt.GetActiveDebtsUseCase;
import com.nyatetduwit.domain.usecase.debt.GetDebtByIdUseCase;
import com.nyatetduwit.domain.usecase.debt.GetDebtPaymentsUseCase;
import com.nyatetduwit.domain.usecase.debt.UpdateDebtUseCase;
import com.nyatetduwit.domain.usecase.goal.AddGoalProgressUseCase;
import com.nyatetduwit.domain.usecase.goal.AddGoalUseCase;
import com.nyatetduwit.domain.usecase.goal.DeleteGoalUseCase;
import com.nyatetduwit.domain.usecase.goal.GetActiveGoalsUseCase;
import com.nyatetduwit.domain.usecase.goal.GetGoalByIdUseCase;
import com.nyatetduwit.domain.usecase.goal.UpdateGoalUseCase;
import com.nyatetduwit.domain.usecase.habit.HabitTracker;
import com.nyatetduwit.domain.usecase.recurring.AddRecurringTransactionUseCase;
import com.nyatetduwit.domain.usecase.recurring.DeactivateRecurringTransactionUseCase;
import com.nyatetduwit.domain.usecase.recurring.DeleteRecurringTransactionUseCase;
import com.nyatetduwit.domain.usecase.recurring.GetAllRecurringTransactionsUseCase;
import com.nyatetduwit.domain.usecase.recurring.GetRecurringTransactionsUseCase;
import com.nyatetduwit.domain.usecase.recurring.SkipRecurringInstanceUseCase;
import com.nyatetduwit.domain.usecase.recurring.UpdateRecurringTransactionUseCase;
import com.nyatetduwit.domain.usecase.template.CreateTemplateFromTransactionUseCase;
import com.nyatetduwit.domain.usecase.template.DeleteTemplateUseCase;
import com.nyatetduwit.domain.usecase.template.GetPinnedTemplatesUseCase;
import com.nyatetduwit.domain.usecase.template.GetTemplateByIdUseCase;
import com.nyatetduwit.domain.usecase.template.GetTemplatesUseCase;
import com.nyatetduwit.domain.usecase.template.IncrementTemplateUsageUseCase;
import com.nyatetduwit.domain.usecase.template.ToggleTemplatePinUseCase;
import com.nyatetduwit.domain.usecase.transaction.AddTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.AnomalyDetectionUseCase;
import com.nyatetduwit.domain.usecase.transaction.BalanceUpdateService;
import com.nyatetduwit.domain.usecase.transaction.GetActiveDaysCountUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetBiggestExpenseUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetDailyExpenseTrendUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetMonthComparisonUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetMonthlyExpenseUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetMonthlyIncomeUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetRecentTransactionsUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetSplitsByTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetSumByTypeAndDateRangeUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTagsByTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTopCategoriesByExpenseUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTransactionByIdUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTransactionCountUseCase;
import com.nyatetduwit.domain.usecase.transaction.RestoreTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.SaveSplitsUseCase;
import com.nyatetduwit.domain.usecase.transaction.SaveTagsUseCase;
import com.nyatetduwit.domain.usecase.transaction.SearchAndFilterTransactionsUseCase;
import com.nyatetduwit.domain.usecase.transaction.SoftDeleteTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.UpdateTransactionUseCase;
import com.nyatetduwit.presentation.account.AccountViewModel;
import com.nyatetduwit.presentation.account.AccountViewModel_HiltModules;
import com.nyatetduwit.presentation.budget.BudgetViewModel;
import com.nyatetduwit.presentation.budget.BudgetViewModel_HiltModules;
import com.nyatetduwit.presentation.cashflow.CashflowTrendViewModel;
import com.nyatetduwit.presentation.cashflow.CashflowTrendViewModel_HiltModules;
import com.nyatetduwit.presentation.category.CategoryViewModel;
import com.nyatetduwit.presentation.category.CategoryViewModel_HiltModules;
import com.nyatetduwit.presentation.dashboard.DashboardViewModel;
import com.nyatetduwit.presentation.dashboard.DashboardViewModel_HiltModules;
import com.nyatetduwit.presentation.debt.DebtViewModel;
import com.nyatetduwit.presentation.debt.DebtViewModel_HiltModules;
import com.nyatetduwit.presentation.goal.GoalViewModel;
import com.nyatetduwit.presentation.goal.GoalViewModel_HiltModules;
import com.nyatetduwit.presentation.monthlysummary.MonthlySummaryViewModel;
import com.nyatetduwit.presentation.monthlysummary.MonthlySummaryViewModel_HiltModules;
import com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel;
import com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel_HiltModules;
import com.nyatetduwit.presentation.remindersettings.ReminderSettingsViewModel;
import com.nyatetduwit.presentation.remindersettings.ReminderSettingsViewModel_HiltModules;
import com.nyatetduwit.presentation.settings.SettingsViewModel;
import com.nyatetduwit.presentation.settings.SettingsViewModel_HiltModules;
import com.nyatetduwit.presentation.template.TemplateViewModel;
import com.nyatetduwit.presentation.template.TemplateViewModel_HiltModules;
import com.nyatetduwit.presentation.transaction.TransactionDetailViewModel;
import com.nyatetduwit.presentation.transaction.TransactionDetailViewModel_HiltModules;
import com.nyatetduwit.presentation.transaction.TransactionListViewModel;
import com.nyatetduwit.presentation.transaction.TransactionListViewModel_HiltModules;
import com.nyatetduwit.presentation.transaction.TransactionViewModel;
import com.nyatetduwit.presentation.transaction.TransactionViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DaggerNyatetDuwitApp_HiltComponents_SingletonC {
  private DaggerNyatetDuwitApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public NyatetDuwitApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements NyatetDuwitApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public NyatetDuwitApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements NyatetDuwitApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public NyatetDuwitApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements NyatetDuwitApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public NyatetDuwitApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements NyatetDuwitApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NyatetDuwitApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements NyatetDuwitApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NyatetDuwitApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements NyatetDuwitApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public NyatetDuwitApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements NyatetDuwitApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public NyatetDuwitApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends NyatetDuwitApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends NyatetDuwitApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends NyatetDuwitApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends NyatetDuwitApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(15).put(LazyClassKeyProvider.com_nyatetduwit_presentation_account_AccountViewModel, AccountViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_budget_BudgetViewModel, BudgetViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_cashflow_CashflowTrendViewModel, CashflowTrendViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_category_CategoryViewModel, CategoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_debt_DebtViewModel, DebtViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_goal_GoalViewModel, GoalViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_monthlysummary_MonthlySummaryViewModel, MonthlySummaryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel, RecurringTransactionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_remindersettings_ReminderSettingsViewModel, ReminderSettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_template_TemplateViewModel, TemplateViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionDetailViewModel, TransactionDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionListViewModel, TransactionListViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionViewModel, TransactionViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_nyatetduwit_presentation_category_CategoryViewModel = "com.nyatetduwit.presentation.category.CategoryViewModel";

      static String com_nyatetduwit_presentation_dashboard_DashboardViewModel = "com.nyatetduwit.presentation.dashboard.DashboardViewModel";

      static String com_nyatetduwit_presentation_monthlysummary_MonthlySummaryViewModel = "com.nyatetduwit.presentation.monthlysummary.MonthlySummaryViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionDetailViewModel = "com.nyatetduwit.presentation.transaction.TransactionDetailViewModel";

      static String com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel = "com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel";

      static String com_nyatetduwit_presentation_template_TemplateViewModel = "com.nyatetduwit.presentation.template.TemplateViewModel";

      static String com_nyatetduwit_presentation_settings_SettingsViewModel = "com.nyatetduwit.presentation.settings.SettingsViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionListViewModel = "com.nyatetduwit.presentation.transaction.TransactionListViewModel";

      static String com_nyatetduwit_presentation_account_AccountViewModel = "com.nyatetduwit.presentation.account.AccountViewModel";

      static String com_nyatetduwit_presentation_budget_BudgetViewModel = "com.nyatetduwit.presentation.budget.BudgetViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionViewModel = "com.nyatetduwit.presentation.transaction.TransactionViewModel";

      static String com_nyatetduwit_presentation_cashflow_CashflowTrendViewModel = "com.nyatetduwit.presentation.cashflow.CashflowTrendViewModel";

      static String com_nyatetduwit_presentation_debt_DebtViewModel = "com.nyatetduwit.presentation.debt.DebtViewModel";

      static String com_nyatetduwit_presentation_goal_GoalViewModel = "com.nyatetduwit.presentation.goal.GoalViewModel";

      static String com_nyatetduwit_presentation_remindersettings_ReminderSettingsViewModel = "com.nyatetduwit.presentation.remindersettings.ReminderSettingsViewModel";

      @KeepFieldType
      CategoryViewModel com_nyatetduwit_presentation_category_CategoryViewModel2;

      @KeepFieldType
      DashboardViewModel com_nyatetduwit_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      MonthlySummaryViewModel com_nyatetduwit_presentation_monthlysummary_MonthlySummaryViewModel2;

      @KeepFieldType
      TransactionDetailViewModel com_nyatetduwit_presentation_transaction_TransactionDetailViewModel2;

      @KeepFieldType
      RecurringTransactionViewModel com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel2;

      @KeepFieldType
      TemplateViewModel com_nyatetduwit_presentation_template_TemplateViewModel2;

      @KeepFieldType
      SettingsViewModel com_nyatetduwit_presentation_settings_SettingsViewModel2;

      @KeepFieldType
      TransactionListViewModel com_nyatetduwit_presentation_transaction_TransactionListViewModel2;

      @KeepFieldType
      AccountViewModel com_nyatetduwit_presentation_account_AccountViewModel2;

      @KeepFieldType
      BudgetViewModel com_nyatetduwit_presentation_budget_BudgetViewModel2;

      @KeepFieldType
      TransactionViewModel com_nyatetduwit_presentation_transaction_TransactionViewModel2;

      @KeepFieldType
      CashflowTrendViewModel com_nyatetduwit_presentation_cashflow_CashflowTrendViewModel2;

      @KeepFieldType
      DebtViewModel com_nyatetduwit_presentation_debt_DebtViewModel2;

      @KeepFieldType
      GoalViewModel com_nyatetduwit_presentation_goal_GoalViewModel2;

      @KeepFieldType
      ReminderSettingsViewModel com_nyatetduwit_presentation_remindersettings_ReminderSettingsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends NyatetDuwitApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AccountViewModel> accountViewModelProvider;

    private Provider<BudgetViewModel> budgetViewModelProvider;

    private Provider<CashflowTrendViewModel> cashflowTrendViewModelProvider;

    private Provider<CategoryViewModel> categoryViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<DebtViewModel> debtViewModelProvider;

    private Provider<GoalViewModel> goalViewModelProvider;

    private Provider<MonthlySummaryViewModel> monthlySummaryViewModelProvider;

    private Provider<RecurringTransactionViewModel> recurringTransactionViewModelProvider;

    private Provider<ReminderSettingsViewModel> reminderSettingsViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<TemplateViewModel> templateViewModelProvider;

    private Provider<TransactionDetailViewModel> transactionDetailViewModelProvider;

    private Provider<TransactionListViewModel> transactionListViewModelProvider;

    private Provider<TransactionViewModel> transactionViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private GetAccountsUseCase getAccountsUseCase() {
      return new GetAccountsUseCase(singletonCImpl.bindAccountRepositoryProvider.get());
    }

    private GetAccountByIdUseCase getAccountByIdUseCase() {
      return new GetAccountByIdUseCase(singletonCImpl.bindAccountRepositoryProvider.get());
    }

    private GetTotalBalanceUseCase getTotalBalanceUseCase() {
      return new GetTotalBalanceUseCase(singletonCImpl.bindAccountRepositoryProvider.get());
    }

    private AddAccountUseCase addAccountUseCase() {
      return new AddAccountUseCase(singletonCImpl.bindAccountRepositoryProvider.get());
    }

    private UpdateAccountUseCase updateAccountUseCase() {
      return new UpdateAccountUseCase(singletonCImpl.bindAccountRepositoryProvider.get());
    }

    private DeleteAccountUseCase deleteAccountUseCase() {
      return new DeleteAccountUseCase(singletonCImpl.bindAccountRepositoryProvider.get());
    }

    private CheckAccountTransactionsUseCase checkAccountTransactionsUseCase() {
      return new CheckAccountTransactionsUseCase(singletonCImpl.bindAccountRepositoryProvider.get());
    }

    private GetBudgetsUseCase getBudgetsUseCase() {
      return new GetBudgetsUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private GetCategoriesUseCase getCategoriesUseCase() {
      return new GetCategoriesUseCase(singletonCImpl.bindCategoryRepositoryProvider.get());
    }

    private AddBudgetUseCase addBudgetUseCase() {
      return new AddBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private UpdateBudgetUseCase updateBudgetUseCase() {
      return new UpdateBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private DeleteBudgetUseCase deleteBudgetUseCase() {
      return new DeleteBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private DeactivateBudgetUseCase deactivateBudgetUseCase() {
      return new DeactivateBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private GetDailyExpenseTrendUseCase getDailyExpenseTrendUseCase() {
      return new GetDailyExpenseTrendUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetTopCategoriesByExpenseUseCase getTopCategoriesByExpenseUseCase() {
      return new GetTopCategoriesByExpenseUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private AnomalyDetectionUseCase anomalyDetectionUseCase() {
      return new AnomalyDetectionUseCase(singletonCImpl.bindTransactionRepositoryProvider.get(), getDailyExpenseTrendUseCase(), getTopCategoriesByExpenseUseCase());
    }

    private AddCategoryUseCase addCategoryUseCase() {
      return new AddCategoryUseCase(singletonCImpl.bindCategoryRepositoryProvider.get());
    }

    private UpdateCategoryUseCase updateCategoryUseCase() {
      return new UpdateCategoryUseCase(singletonCImpl.bindCategoryRepositoryProvider.get());
    }

    private DeleteCategoryUseCase deleteCategoryUseCase() {
      return new DeleteCategoryUseCase(singletonCImpl.bindCategoryRepositoryProvider.get());
    }

    private SeedDefaultCategoriesUseCase seedDefaultCategoriesUseCase() {
      return new SeedDefaultCategoriesUseCase(singletonCImpl.bindCategoryRepositoryProvider.get());
    }

    private GetSumByTypeAndDateRangeUseCase getSumByTypeAndDateRangeUseCase() {
      return new GetSumByTypeAndDateRangeUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetRecentTransactionsUseCase getRecentTransactionsUseCase() {
      return new GetRecentTransactionsUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetMonthlyExpenseUseCase getMonthlyExpenseUseCase() {
      return new GetMonthlyExpenseUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetMonthlyIncomeUseCase getMonthlyIncomeUseCase() {
      return new GetMonthlyIncomeUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetTransactionCountUseCase getTransactionCountUseCase() {
      return new GetTransactionCountUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetActiveDebtsUseCase getActiveDebtsUseCase() {
      return new GetActiveDebtsUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private GetDebtByIdUseCase getDebtByIdUseCase() {
      return new GetDebtByIdUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private AddDebtUseCase addDebtUseCase() {
      return new AddDebtUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private UpdateDebtUseCase updateDebtUseCase() {
      return new UpdateDebtUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private DeleteDebtUseCase deleteDebtUseCase() {
      return new DeleteDebtUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private GetDebtPaymentsUseCase getDebtPaymentsUseCase() {
      return new GetDebtPaymentsUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private AddDebtPaymentUseCase addDebtPaymentUseCase() {
      return new AddDebtPaymentUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private DeleteDebtPaymentUseCase deleteDebtPaymentUseCase() {
      return new DeleteDebtPaymentUseCase(singletonCImpl.bindDebtRepositoryProvider.get());
    }

    private GetActiveGoalsUseCase getActiveGoalsUseCase() {
      return new GetActiveGoalsUseCase(singletonCImpl.bindGoalRepositoryProvider.get());
    }

    private GetGoalByIdUseCase getGoalByIdUseCase() {
      return new GetGoalByIdUseCase(singletonCImpl.bindGoalRepositoryProvider.get());
    }

    private AddGoalUseCase addGoalUseCase() {
      return new AddGoalUseCase(singletonCImpl.bindGoalRepositoryProvider.get());
    }

    private UpdateGoalUseCase updateGoalUseCase() {
      return new UpdateGoalUseCase(singletonCImpl.bindGoalRepositoryProvider.get());
    }

    private DeleteGoalUseCase deleteGoalUseCase() {
      return new DeleteGoalUseCase(singletonCImpl.bindGoalRepositoryProvider.get());
    }

    private AddGoalProgressUseCase addGoalProgressUseCase() {
      return new AddGoalProgressUseCase(singletonCImpl.bindGoalRepositoryProvider.get());
    }

    private GetBiggestExpenseUseCase getBiggestExpenseUseCase() {
      return new GetBiggestExpenseUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetMonthComparisonUseCase getMonthComparisonUseCase() {
      return new GetMonthComparisonUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetActiveDaysCountUseCase getActiveDaysCountUseCase() {
      return new GetActiveDaysCountUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetRecurringTransactionsUseCase getRecurringTransactionsUseCase() {
      return new GetRecurringTransactionsUseCase(singletonCImpl.bindRecurringTransactionRepositoryProvider.get());
    }

    private GetAllRecurringTransactionsUseCase getAllRecurringTransactionsUseCase() {
      return new GetAllRecurringTransactionsUseCase(singletonCImpl.bindRecurringTransactionRepositoryProvider.get());
    }

    private AddRecurringTransactionUseCase addRecurringTransactionUseCase() {
      return new AddRecurringTransactionUseCase(singletonCImpl.bindRecurringTransactionRepositoryProvider.get());
    }

    private UpdateRecurringTransactionUseCase updateRecurringTransactionUseCase() {
      return new UpdateRecurringTransactionUseCase(singletonCImpl.bindRecurringTransactionRepositoryProvider.get());
    }

    private DeleteRecurringTransactionUseCase deleteRecurringTransactionUseCase() {
      return new DeleteRecurringTransactionUseCase(singletonCImpl.bindRecurringTransactionRepositoryProvider.get());
    }

    private DeactivateRecurringTransactionUseCase deactivateRecurringTransactionUseCase() {
      return new DeactivateRecurringTransactionUseCase(singletonCImpl.bindRecurringTransactionRepositoryProvider.get());
    }

    private SkipRecurringInstanceUseCase skipRecurringInstanceUseCase() {
      return new SkipRecurringInstanceUseCase(singletonCImpl.bindRecurringTransactionRepositoryProvider.get());
    }

    private PdfExportManager pdfExportManager() {
      return new PdfExportManager(singletonCImpl.transactionDao());
    }

    private GetTemplatesUseCase getTemplatesUseCase() {
      return new GetTemplatesUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    private DeleteTemplateUseCase deleteTemplateUseCase() {
      return new DeleteTemplateUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    private ToggleTemplatePinUseCase toggleTemplatePinUseCase() {
      return new ToggleTemplatePinUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    private GetTransactionByIdUseCase getTransactionByIdUseCase() {
      return new GetTransactionByIdUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetCategoryByIdUseCase getCategoryByIdUseCase() {
      return new GetCategoryByIdUseCase(singletonCImpl.bindCategoryRepositoryProvider.get());
    }

    private SoftDeleteTransactionUseCase softDeleteTransactionUseCase() {
      return new SoftDeleteTransactionUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private BalanceUpdateService balanceUpdateService() {
      return new BalanceUpdateService(singletonCImpl.bindAccountRepositoryProvider.get(), singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetSplitsByTransactionUseCase getSplitsByTransactionUseCase() {
      return new GetSplitsByTransactionUseCase(singletonCImpl.bindTransactionSplitRepositoryProvider.get());
    }

    private GetTagsByTransactionUseCase getTagsByTransactionUseCase() {
      return new GetTagsByTransactionUseCase(singletonCImpl.bindTransactionTagRepositoryProvider.get());
    }

    private SearchAndFilterTransactionsUseCase searchAndFilterTransactionsUseCase() {
      return new SearchAndFilterTransactionsUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private RestoreTransactionUseCase restoreTransactionUseCase() {
      return new RestoreTransactionUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private CreateTemplateFromTransactionUseCase createTemplateFromTransactionUseCase() {
      return new CreateTemplateFromTransactionUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    private GetCategoriesByTypeUseCase getCategoriesByTypeUseCase() {
      return new GetCategoriesByTypeUseCase(singletonCImpl.bindCategoryRepositoryProvider.get());
    }

    private AddTransactionUseCase addTransactionUseCase() {
      return new AddTransactionUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private UpdateTransactionUseCase updateTransactionUseCase() {
      return new UpdateTransactionUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetPinnedTemplatesUseCase getPinnedTemplatesUseCase() {
      return new GetPinnedTemplatesUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    private GetTemplateByIdUseCase getTemplateByIdUseCase() {
      return new GetTemplateByIdUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    private IncrementTemplateUsageUseCase incrementTemplateUsageUseCase() {
      return new IncrementTemplateUsageUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    private SaveSplitsUseCase saveSplitsUseCase() {
      return new SaveSplitsUseCase(singletonCImpl.bindTransactionSplitRepositoryProvider.get());
    }

    private SaveTagsUseCase saveTagsUseCase() {
      return new SaveTagsUseCase(singletonCImpl.bindTransactionTagRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.accountViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.budgetViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.cashflowTrendViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.categoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.debtViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.goalViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.monthlySummaryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.recurringTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.reminderSettingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.templateViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.transactionDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.transactionListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
      this.transactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 14);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(15).put(LazyClassKeyProvider.com_nyatetduwit_presentation_account_AccountViewModel, ((Provider) accountViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_budget_BudgetViewModel, ((Provider) budgetViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_cashflow_CashflowTrendViewModel, ((Provider) cashflowTrendViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_category_CategoryViewModel, ((Provider) categoryViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_debt_DebtViewModel, ((Provider) debtViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_goal_GoalViewModel, ((Provider) goalViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_monthlysummary_MonthlySummaryViewModel, ((Provider) monthlySummaryViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel, ((Provider) recurringTransactionViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_remindersettings_ReminderSettingsViewModel, ((Provider) reminderSettingsViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_template_TemplateViewModel, ((Provider) templateViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionDetailViewModel, ((Provider) transactionDetailViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionListViewModel, ((Provider) transactionListViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionViewModel, ((Provider) transactionViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_nyatetduwit_presentation_category_CategoryViewModel = "com.nyatetduwit.presentation.category.CategoryViewModel";

      static String com_nyatetduwit_presentation_account_AccountViewModel = "com.nyatetduwit.presentation.account.AccountViewModel";

      static String com_nyatetduwit_presentation_settings_SettingsViewModel = "com.nyatetduwit.presentation.settings.SettingsViewModel";

      static String com_nyatetduwit_presentation_dashboard_DashboardViewModel = "com.nyatetduwit.presentation.dashboard.DashboardViewModel";

      static String com_nyatetduwit_presentation_template_TemplateViewModel = "com.nyatetduwit.presentation.template.TemplateViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionViewModel = "com.nyatetduwit.presentation.transaction.TransactionViewModel";

      static String com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel = "com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel";

      static String com_nyatetduwit_presentation_goal_GoalViewModel = "com.nyatetduwit.presentation.goal.GoalViewModel";

      static String com_nyatetduwit_presentation_remindersettings_ReminderSettingsViewModel = "com.nyatetduwit.presentation.remindersettings.ReminderSettingsViewModel";

      static String com_nyatetduwit_presentation_debt_DebtViewModel = "com.nyatetduwit.presentation.debt.DebtViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionListViewModel = "com.nyatetduwit.presentation.transaction.TransactionListViewModel";

      static String com_nyatetduwit_presentation_budget_BudgetViewModel = "com.nyatetduwit.presentation.budget.BudgetViewModel";

      static String com_nyatetduwit_presentation_cashflow_CashflowTrendViewModel = "com.nyatetduwit.presentation.cashflow.CashflowTrendViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionDetailViewModel = "com.nyatetduwit.presentation.transaction.TransactionDetailViewModel";

      static String com_nyatetduwit_presentation_monthlysummary_MonthlySummaryViewModel = "com.nyatetduwit.presentation.monthlysummary.MonthlySummaryViewModel";

      @KeepFieldType
      CategoryViewModel com_nyatetduwit_presentation_category_CategoryViewModel2;

      @KeepFieldType
      AccountViewModel com_nyatetduwit_presentation_account_AccountViewModel2;

      @KeepFieldType
      SettingsViewModel com_nyatetduwit_presentation_settings_SettingsViewModel2;

      @KeepFieldType
      DashboardViewModel com_nyatetduwit_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      TemplateViewModel com_nyatetduwit_presentation_template_TemplateViewModel2;

      @KeepFieldType
      TransactionViewModel com_nyatetduwit_presentation_transaction_TransactionViewModel2;

      @KeepFieldType
      RecurringTransactionViewModel com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel2;

      @KeepFieldType
      GoalViewModel com_nyatetduwit_presentation_goal_GoalViewModel2;

      @KeepFieldType
      ReminderSettingsViewModel com_nyatetduwit_presentation_remindersettings_ReminderSettingsViewModel2;

      @KeepFieldType
      DebtViewModel com_nyatetduwit_presentation_debt_DebtViewModel2;

      @KeepFieldType
      TransactionListViewModel com_nyatetduwit_presentation_transaction_TransactionListViewModel2;

      @KeepFieldType
      BudgetViewModel com_nyatetduwit_presentation_budget_BudgetViewModel2;

      @KeepFieldType
      CashflowTrendViewModel com_nyatetduwit_presentation_cashflow_CashflowTrendViewModel2;

      @KeepFieldType
      TransactionDetailViewModel com_nyatetduwit_presentation_transaction_TransactionDetailViewModel2;

      @KeepFieldType
      MonthlySummaryViewModel com_nyatetduwit_presentation_monthlysummary_MonthlySummaryViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.nyatetduwit.presentation.account.AccountViewModel 
          return (T) new AccountViewModel(viewModelCImpl.getAccountsUseCase(), viewModelCImpl.getAccountByIdUseCase(), viewModelCImpl.getTotalBalanceUseCase(), viewModelCImpl.addAccountUseCase(), viewModelCImpl.updateAccountUseCase(), viewModelCImpl.deleteAccountUseCase(), viewModelCImpl.checkAccountTransactionsUseCase());

          case 1: // com.nyatetduwit.presentation.budget.BudgetViewModel 
          return (T) new BudgetViewModel(viewModelCImpl.getBudgetsUseCase(), viewModelCImpl.getCategoriesUseCase(), singletonCImpl.bindBudgetRepositoryProvider.get(), viewModelCImpl.addBudgetUseCase(), viewModelCImpl.updateBudgetUseCase(), viewModelCImpl.deleteBudgetUseCase(), viewModelCImpl.deactivateBudgetUseCase());

          case 2: // com.nyatetduwit.presentation.cashflow.CashflowTrendViewModel 
          return (T) new CashflowTrendViewModel(viewModelCImpl.anomalyDetectionUseCase());

          case 3: // com.nyatetduwit.presentation.category.CategoryViewModel 
          return (T) new CategoryViewModel(viewModelCImpl.getCategoriesUseCase(), viewModelCImpl.addCategoryUseCase(), viewModelCImpl.updateCategoryUseCase(), viewModelCImpl.deleteCategoryUseCase(), viewModelCImpl.seedDefaultCategoriesUseCase());

          case 4: // com.nyatetduwit.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(viewModelCImpl.getTotalBalanceUseCase(), viewModelCImpl.getSumByTypeAndDateRangeUseCase(), viewModelCImpl.getTopCategoriesByExpenseUseCase(), viewModelCImpl.getRecentTransactionsUseCase(), viewModelCImpl.getCategoriesUseCase(), singletonCImpl.bindSettingsRepositoryProvider.get(), singletonCImpl.habitTrackerProvider.get(), viewModelCImpl.getMonthlyExpenseUseCase(), viewModelCImpl.getMonthlyIncomeUseCase(), viewModelCImpl.getTransactionCountUseCase(), singletonCImpl.bindBudgetRepositoryProvider.get());

          case 5: // com.nyatetduwit.presentation.debt.DebtViewModel 
          return (T) new DebtViewModel(viewModelCImpl.getActiveDebtsUseCase(), viewModelCImpl.getDebtByIdUseCase(), viewModelCImpl.addDebtUseCase(), viewModelCImpl.updateDebtUseCase(), viewModelCImpl.deleteDebtUseCase(), viewModelCImpl.getDebtPaymentsUseCase(), viewModelCImpl.addDebtPaymentUseCase(), viewModelCImpl.deleteDebtPaymentUseCase());

          case 6: // com.nyatetduwit.presentation.goal.GoalViewModel 
          return (T) new GoalViewModel(viewModelCImpl.getActiveGoalsUseCase(), viewModelCImpl.getGoalByIdUseCase(), viewModelCImpl.addGoalUseCase(), viewModelCImpl.updateGoalUseCase(), viewModelCImpl.deleteGoalUseCase(), viewModelCImpl.addGoalProgressUseCase());

          case 7: // com.nyatetduwit.presentation.monthlysummary.MonthlySummaryViewModel 
          return (T) new MonthlySummaryViewModel(viewModelCImpl.getSumByTypeAndDateRangeUseCase(), viewModelCImpl.getTopCategoriesByExpenseUseCase(), viewModelCImpl.getDailyExpenseTrendUseCase(), viewModelCImpl.getBiggestExpenseUseCase(), viewModelCImpl.getMonthComparisonUseCase(), viewModelCImpl.getActiveDaysCountUseCase(), viewModelCImpl.getTransactionCountUseCase(), viewModelCImpl.getCategoriesUseCase());

          case 8: // com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel 
          return (T) new RecurringTransactionViewModel(viewModelCImpl.getRecurringTransactionsUseCase(), viewModelCImpl.getAllRecurringTransactionsUseCase(), singletonCImpl.bindRecurringTransactionRepositoryProvider.get(), singletonCImpl.bindTransactionRepositoryProvider.get(), viewModelCImpl.addRecurringTransactionUseCase(), viewModelCImpl.updateRecurringTransactionUseCase(), viewModelCImpl.deleteRecurringTransactionUseCase(), viewModelCImpl.deactivateRecurringTransactionUseCase(), viewModelCImpl.skipRecurringInstanceUseCase(), viewModelCImpl.getAccountsUseCase(), viewModelCImpl.getCategoriesUseCase());

          case 9: // com.nyatetduwit.presentation.remindersettings.ReminderSettingsViewModel 
          return (T) new ReminderSettingsViewModel(singletonCImpl.bindSettingsRepositoryProvider.get());

          case 10: // com.nyatetduwit.presentation.settings.SettingsViewModel 
          return (T) new SettingsViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.bindSettingsRepositoryProvider.get(), singletonCImpl.provideExportManagerProvider.get(), viewModelCImpl.pdfExportManager(), singletonCImpl.reminderSchedulerProvider.get());

          case 11: // com.nyatetduwit.presentation.template.TemplateViewModel 
          return (T) new TemplateViewModel(viewModelCImpl.getTemplatesUseCase(), viewModelCImpl.deleteTemplateUseCase(), viewModelCImpl.toggleTemplatePinUseCase());

          case 12: // com.nyatetduwit.presentation.transaction.TransactionDetailViewModel 
          return (T) new TransactionDetailViewModel(viewModelCImpl.getTransactionByIdUseCase(), viewModelCImpl.getAccountByIdUseCase(), viewModelCImpl.getCategoryByIdUseCase(), viewModelCImpl.softDeleteTransactionUseCase(), viewModelCImpl.balanceUpdateService(), viewModelCImpl.getSplitsByTransactionUseCase(), viewModelCImpl.getTagsByTransactionUseCase());

          case 13: // com.nyatetduwit.presentation.transaction.TransactionListViewModel 
          return (T) new TransactionListViewModel(viewModelCImpl.searchAndFilterTransactionsUseCase(), viewModelCImpl.softDeleteTransactionUseCase(), viewModelCImpl.restoreTransactionUseCase(), viewModelCImpl.getAccountsUseCase(), viewModelCImpl.getCategoriesUseCase(), viewModelCImpl.createTemplateFromTransactionUseCase());

          case 14: // com.nyatetduwit.presentation.transaction.TransactionViewModel 
          return (T) new TransactionViewModel(viewModelCImpl.getAccountsUseCase(), viewModelCImpl.getCategoriesByTypeUseCase(), viewModelCImpl.addTransactionUseCase(), viewModelCImpl.updateTransactionUseCase(), viewModelCImpl.getTransactionByIdUseCase(), viewModelCImpl.balanceUpdateService(), viewModelCImpl.getPinnedTemplatesUseCase(), viewModelCImpl.getTemplateByIdUseCase(), viewModelCImpl.incrementTemplateUsageUseCase(), singletonCImpl.bindSettingsRepositoryProvider.get(), viewModelCImpl.getSplitsByTransactionUseCase(), viewModelCImpl.getTagsByTransactionUseCase(), viewModelCImpl.saveSplitsUseCase(), viewModelCImpl.saveTagsUseCase());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends NyatetDuwitApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends NyatetDuwitApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends NyatetDuwitApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<NyatetDuwitDatabase> provideDatabaseProvider;

    private Provider<ExportManager> provideExportManagerProvider;

    private Provider<AutoBackupWorker_AssistedFactory> autoBackupWorker_AssistedFactoryProvider;

    private Provider<RecurringTransactionRepositoryImpl> recurringTransactionRepositoryImplProvider;

    private Provider<RecurringTransactionRepository> bindRecurringTransactionRepositoryProvider;

    private Provider<TransactionRepositoryImpl> transactionRepositoryImplProvider;

    private Provider<TransactionRepository> bindTransactionRepositoryProvider;

    private Provider<RecurringTransactionWorker_AssistedFactory> recurringTransactionWorker_AssistedFactoryProvider;

    private Provider<DataStore<Preferences>> provideDataStoreProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<SettingsRepository> bindSettingsRepositoryProvider;

    private Provider<ReminderWorker_AssistedFactory> reminderWorker_AssistedFactoryProvider;

    private Provider<AccountRepositoryImpl> accountRepositoryImplProvider;

    private Provider<AccountRepository> bindAccountRepositoryProvider;

    private Provider<BudgetRepositoryImpl> budgetRepositoryImplProvider;

    private Provider<BudgetRepository> bindBudgetRepositoryProvider;

    private Provider<CategoryRepositoryImpl> categoryRepositoryImplProvider;

    private Provider<CategoryRepository> bindCategoryRepositoryProvider;

    private Provider<HabitTracker> habitTrackerProvider;

    private Provider<DebtRepositoryImpl> debtRepositoryImplProvider;

    private Provider<DebtRepository> bindDebtRepositoryProvider;

    private Provider<GoalRepositoryImpl> goalRepositoryImplProvider;

    private Provider<GoalRepository> bindGoalRepositoryProvider;

    private Provider<ReminderScheduler> reminderSchedulerProvider;

    private Provider<TemplateRepositoryImpl> templateRepositoryImplProvider;

    private Provider<TemplateRepository> bindTemplateRepositoryProvider;

    private Provider<TransactionSplitRepositoryImpl> transactionSplitRepositoryImplProvider;

    private Provider<TransactionSplitRepository> bindTransactionSplitRepositoryProvider;

    private Provider<TransactionTagRepositoryImpl> transactionTagRepositoryImplProvider;

    private Provider<TransactionTagRepository> bindTransactionTagRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);
      initialize2(applicationContextModuleParam);

    }

    private TransactionDao transactionDao() {
      return DatabaseModule_ProvideTransactionDaoFactory.provideTransactionDao(provideDatabaseProvider.get());
    }

    private AccountDao accountDao() {
      return DatabaseModule_ProvideAccountDaoFactory.provideAccountDao(provideDatabaseProvider.get());
    }

    private CategoryDao categoryDao() {
      return DatabaseModule_ProvideCategoryDaoFactory.provideCategoryDao(provideDatabaseProvider.get());
    }

    private BudgetDao budgetDao() {
      return DatabaseModule_ProvideBudgetDaoFactory.provideBudgetDao(provideDatabaseProvider.get());
    }

    private RecurringTransactionDao recurringTransactionDao() {
      return DatabaseModule_ProvideRecurringTransactionDaoFactory.provideRecurringTransactionDao(provideDatabaseProvider.get());
    }

    private TemplateDao templateDao() {
      return DatabaseModule_ProvideTemplateDaoFactory.provideTemplateDao(provideDatabaseProvider.get());
    }

    private TransactionSplitDao transactionSplitDao() {
      return DatabaseModule_ProvideTransactionSplitDaoFactory.provideTransactionSplitDao(provideDatabaseProvider.get());
    }

    private TransactionTagDao transactionTagDao() {
      return DatabaseModule_ProvideTransactionTagDaoFactory.provideTransactionTagDao(provideDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return MapBuilder.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>newMapBuilder(3).put("com.nyatetduwit.core.worker.AutoBackupWorker", ((Provider) autoBackupWorker_AssistedFactoryProvider)).put("com.nyatetduwit.core.worker.RecurringTransactionWorker", ((Provider) recurringTransactionWorker_AssistedFactoryProvider)).put("com.nyatetduwit.core.worker.ReminderWorker", ((Provider) reminderWorker_AssistedFactoryProvider)).build();
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private DebtDao debtDao() {
      return DatabaseModule_ProvideDebtDaoFactory.provideDebtDao(provideDatabaseProvider.get());
    }

    private GoalDao goalDao() {
      return DatabaseModule_ProvideGoalDaoFactory.provideGoalDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<NyatetDuwitDatabase>(singletonCImpl, 2));
      this.provideExportManagerProvider = DoubleCheck.provider(new SwitchingProvider<ExportManager>(singletonCImpl, 1));
      this.autoBackupWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<AutoBackupWorker_AssistedFactory>(singletonCImpl, 0));
      this.recurringTransactionRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 4);
      this.bindRecurringTransactionRepositoryProvider = DoubleCheck.provider((Provider) recurringTransactionRepositoryImplProvider);
      this.transactionRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 5);
      this.bindTransactionRepositoryProvider = DoubleCheck.provider((Provider) transactionRepositoryImplProvider);
      this.recurringTransactionWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<RecurringTransactionWorker_AssistedFactory>(singletonCImpl, 3));
      this.provideDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 8));
      this.settingsRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 7);
      this.bindSettingsRepositoryProvider = DoubleCheck.provider((Provider) settingsRepositoryImplProvider);
      this.reminderWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<ReminderWorker_AssistedFactory>(singletonCImpl, 6));
      this.accountRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 9);
      this.bindAccountRepositoryProvider = DoubleCheck.provider((Provider) accountRepositoryImplProvider);
      this.budgetRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 10);
      this.bindBudgetRepositoryProvider = DoubleCheck.provider((Provider) budgetRepositoryImplProvider);
      this.categoryRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 11);
      this.bindCategoryRepositoryProvider = DoubleCheck.provider((Provider) categoryRepositoryImplProvider);
      this.habitTrackerProvider = DoubleCheck.provider(new SwitchingProvider<HabitTracker>(singletonCImpl, 12));
      this.debtRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 13);
      this.bindDebtRepositoryProvider = DoubleCheck.provider((Provider) debtRepositoryImplProvider);
      this.goalRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 14);
      this.bindGoalRepositoryProvider = DoubleCheck.provider((Provider) goalRepositoryImplProvider);
      this.reminderSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<ReminderScheduler>(singletonCImpl, 15));
      this.templateRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 16);
    }

    @SuppressWarnings("unchecked")
    private void initialize2(final ApplicationContextModule applicationContextModuleParam) {
      this.bindTemplateRepositoryProvider = DoubleCheck.provider((Provider) templateRepositoryImplProvider);
      this.transactionSplitRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 17);
      this.bindTransactionSplitRepositoryProvider = DoubleCheck.provider((Provider) transactionSplitRepositoryImplProvider);
      this.transactionTagRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 18);
      this.bindTransactionTagRepositoryProvider = DoubleCheck.provider((Provider) transactionTagRepositoryImplProvider);
    }

    @Override
    public void injectNyatetDuwitApp(NyatetDuwitApp nyatetDuwitApp) {
      injectNyatetDuwitApp2(nyatetDuwitApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private NyatetDuwitApp injectNyatetDuwitApp2(NyatetDuwitApp instance) {
      NyatetDuwitApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      NyatetDuwitApp_MembersInjector.injectDataStore(instance, provideDataStoreProvider.get());
      NyatetDuwitApp_MembersInjector.injectSettingsRepository(instance, bindSettingsRepositoryProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.nyatetduwit.core.worker.AutoBackupWorker_AssistedFactory 
          return (T) new AutoBackupWorker_AssistedFactory() {
            @Override
            public AutoBackupWorker create(Context appContext, WorkerParameters params) {
              return new AutoBackupWorker(appContext, params, singletonCImpl.provideExportManagerProvider.get());
            }
          };

          case 1: // com.nyatetduwit.data.local.ExportManager 
          return (T) DatabaseModule_ProvideExportManagerFactory.provideExportManager(singletonCImpl.transactionDao(), singletonCImpl.accountDao(), singletonCImpl.categoryDao(), singletonCImpl.budgetDao(), singletonCImpl.recurringTransactionDao(), singletonCImpl.templateDao(), singletonCImpl.transactionSplitDao(), singletonCImpl.transactionTagDao());

          case 2: // com.nyatetduwit.data.local.NyatetDuwitDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.nyatetduwit.core.worker.RecurringTransactionWorker_AssistedFactory 
          return (T) new RecurringTransactionWorker_AssistedFactory() {
            @Override
            public RecurringTransactionWorker create(Context context,
                WorkerParameters workerParams) {
              return new RecurringTransactionWorker(context, workerParams, singletonCImpl.bindRecurringTransactionRepositoryProvider.get(), singletonCImpl.bindTransactionRepositoryProvider.get());
            }
          };

          case 4: // com.nyatetduwit.data.repository.RecurringTransactionRepositoryImpl 
          return (T) new RecurringTransactionRepositoryImpl(singletonCImpl.recurringTransactionDao());

          case 5: // com.nyatetduwit.data.repository.TransactionRepositoryImpl 
          return (T) new TransactionRepositoryImpl(singletonCImpl.transactionDao());

          case 6: // com.nyatetduwit.core.worker.ReminderWorker_AssistedFactory 
          return (T) new ReminderWorker_AssistedFactory() {
            @Override
            public ReminderWorker create(Context context2, WorkerParameters workerParams2) {
              return new ReminderWorker(context2, workerParams2, singletonCImpl.bindSettingsRepositoryProvider.get(), singletonCImpl.bindTransactionRepositoryProvider.get());
            }
          };

          case 7: // com.nyatetduwit.data.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(singletonCImpl.provideDataStoreProvider.get());

          case 8: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DataStoreModule_ProvideDataStoreFactory.provideDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.nyatetduwit.data.repository.AccountRepositoryImpl 
          return (T) new AccountRepositoryImpl(singletonCImpl.accountDao(), singletonCImpl.transactionDao());

          case 10: // com.nyatetduwit.data.repository.BudgetRepositoryImpl 
          return (T) new BudgetRepositoryImpl(singletonCImpl.budgetDao(), singletonCImpl.transactionDao());

          case 11: // com.nyatetduwit.data.repository.CategoryRepositoryImpl 
          return (T) new CategoryRepositoryImpl(singletonCImpl.categoryDao());

          case 12: // com.nyatetduwit.domain.usecase.habit.HabitTracker 
          return (T) new HabitTracker(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.bindSettingsRepositoryProvider.get());

          case 13: // com.nyatetduwit.data.repository.DebtRepositoryImpl 
          return (T) new DebtRepositoryImpl(singletonCImpl.debtDao());

          case 14: // com.nyatetduwit.data.repository.GoalRepositoryImpl 
          return (T) new GoalRepositoryImpl(singletonCImpl.goalDao());

          case 15: // com.nyatetduwit.core.worker.ReminderScheduler 
          return (T) new ReminderScheduler();

          case 16: // com.nyatetduwit.data.repository.TemplateRepositoryImpl 
          return (T) new TemplateRepositoryImpl(singletonCImpl.templateDao(), singletonCImpl.transactionDao());

          case 17: // com.nyatetduwit.data.repository.TransactionSplitRepositoryImpl 
          return (T) new TransactionSplitRepositoryImpl(singletonCImpl.transactionSplitDao());

          case 18: // com.nyatetduwit.data.repository.TransactionTagRepositoryImpl 
          return (T) new TransactionTagRepositoryImpl(singletonCImpl.transactionTagDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
