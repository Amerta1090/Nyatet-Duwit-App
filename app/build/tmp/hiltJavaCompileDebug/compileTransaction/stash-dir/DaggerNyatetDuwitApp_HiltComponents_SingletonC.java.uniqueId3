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
import com.nyatetduwit.core.di.DatabaseModule_ProvideRecurringTransactionDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideTemplateDaoFactory;
import com.nyatetduwit.core.di.DatabaseModule_ProvideTransactionDaoFactory;
import com.nyatetduwit.core.worker.RecurringTransactionWorker;
import com.nyatetduwit.core.worker.RecurringTransactionWorker_AssistedFactory;
import com.nyatetduwit.data.local.NyatetDuwitDatabase;
import com.nyatetduwit.data.local.dao.AccountDao;
import com.nyatetduwit.data.local.dao.BudgetDao;
import com.nyatetduwit.data.local.dao.CategoryDao;
import com.nyatetduwit.data.local.dao.RecurringTransactionDao;
import com.nyatetduwit.data.local.dao.TemplateDao;
import com.nyatetduwit.data.local.dao.TransactionDao;
import com.nyatetduwit.data.repository.AccountRepositoryImpl;
import com.nyatetduwit.data.repository.BudgetRepositoryImpl;
import com.nyatetduwit.data.repository.CategoryRepositoryImpl;
import com.nyatetduwit.data.repository.RecurringTransactionRepositoryImpl;
import com.nyatetduwit.data.repository.SettingsRepositoryImpl;
import com.nyatetduwit.data.repository.TemplateRepositoryImpl;
import com.nyatetduwit.data.repository.TransactionRepositoryImpl;
import com.nyatetduwit.domain.repository.AccountRepository;
import com.nyatetduwit.domain.repository.BudgetRepository;
import com.nyatetduwit.domain.repository.CategoryRepository;
import com.nyatetduwit.domain.repository.RecurringTransactionRepository;
import com.nyatetduwit.domain.repository.SettingsRepository;
import com.nyatetduwit.domain.repository.TemplateRepository;
import com.nyatetduwit.domain.repository.TransactionRepository;
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
import com.nyatetduwit.domain.usecase.template.GetTemplatesUseCase;
import com.nyatetduwit.domain.usecase.template.IncrementTemplateUsageUseCase;
import com.nyatetduwit.domain.usecase.template.ToggleTemplatePinUseCase;
import com.nyatetduwit.domain.usecase.transaction.AddTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.BalanceUpdateService;
import com.nyatetduwit.domain.usecase.transaction.GetRecentTransactionsUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetSumByTypeAndDateRangeUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTopCategoriesByExpenseUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTransactionByIdUseCase;
import com.nyatetduwit.domain.usecase.transaction.RestoreTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.SearchAndFilterTransactionsUseCase;
import com.nyatetduwit.domain.usecase.transaction.SoftDeleteTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.UpdateTransactionUseCase;
import com.nyatetduwit.presentation.account.AccountViewModel;
import com.nyatetduwit.presentation.account.AccountViewModel_HiltModules;
import com.nyatetduwit.presentation.budget.BudgetViewModel;
import com.nyatetduwit.presentation.budget.BudgetViewModel_HiltModules;
import com.nyatetduwit.presentation.category.CategoryViewModel;
import com.nyatetduwit.presentation.category.CategoryViewModel_HiltModules;
import com.nyatetduwit.presentation.dashboard.DashboardViewModel;
import com.nyatetduwit.presentation.dashboard.DashboardViewModel_HiltModules;
import com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel;
import com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel_HiltModules;
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
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(9).put(LazyClassKeyProvider.com_nyatetduwit_presentation_account_AccountViewModel, AccountViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_budget_BudgetViewModel, BudgetViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_category_CategoryViewModel, CategoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel, RecurringTransactionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_template_TemplateViewModel, TemplateViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionDetailViewModel, TransactionDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionListViewModel, TransactionListViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionViewModel, TransactionViewModel_HiltModules.KeyModule.provide()).build());
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
      static String com_nyatetduwit_presentation_transaction_TransactionListViewModel = "com.nyatetduwit.presentation.transaction.TransactionListViewModel";

      static String com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel = "com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel";

      static String com_nyatetduwit_presentation_account_AccountViewModel = "com.nyatetduwit.presentation.account.AccountViewModel";

      static String com_nyatetduwit_presentation_category_CategoryViewModel = "com.nyatetduwit.presentation.category.CategoryViewModel";

      static String com_nyatetduwit_presentation_dashboard_DashboardViewModel = "com.nyatetduwit.presentation.dashboard.DashboardViewModel";

      static String com_nyatetduwit_presentation_budget_BudgetViewModel = "com.nyatetduwit.presentation.budget.BudgetViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionViewModel = "com.nyatetduwit.presentation.transaction.TransactionViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionDetailViewModel = "com.nyatetduwit.presentation.transaction.TransactionDetailViewModel";

      static String com_nyatetduwit_presentation_template_TemplateViewModel = "com.nyatetduwit.presentation.template.TemplateViewModel";

      @KeepFieldType
      TransactionListViewModel com_nyatetduwit_presentation_transaction_TransactionListViewModel2;

      @KeepFieldType
      RecurringTransactionViewModel com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel2;

      @KeepFieldType
      AccountViewModel com_nyatetduwit_presentation_account_AccountViewModel2;

      @KeepFieldType
      CategoryViewModel com_nyatetduwit_presentation_category_CategoryViewModel2;

      @KeepFieldType
      DashboardViewModel com_nyatetduwit_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      BudgetViewModel com_nyatetduwit_presentation_budget_BudgetViewModel2;

      @KeepFieldType
      TransactionViewModel com_nyatetduwit_presentation_transaction_TransactionViewModel2;

      @KeepFieldType
      TransactionDetailViewModel com_nyatetduwit_presentation_transaction_TransactionDetailViewModel2;

      @KeepFieldType
      TemplateViewModel com_nyatetduwit_presentation_template_TemplateViewModel2;
    }
  }

  private static final class ViewModelCImpl extends NyatetDuwitApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AccountViewModel> accountViewModelProvider;

    private Provider<BudgetViewModel> budgetViewModelProvider;

    private Provider<CategoryViewModel> categoryViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<RecurringTransactionViewModel> recurringTransactionViewModelProvider;

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

    private GetTopCategoriesByExpenseUseCase getTopCategoriesByExpenseUseCase() {
      return new GetTopCategoriesByExpenseUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
    }

    private GetRecentTransactionsUseCase getRecentTransactionsUseCase() {
      return new GetRecentTransactionsUseCase(singletonCImpl.bindTransactionRepositoryProvider.get());
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

    private IncrementTemplateUsageUseCase incrementTemplateUsageUseCase() {
      return new IncrementTemplateUsageUseCase(singletonCImpl.bindTemplateRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.accountViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.budgetViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.categoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.recurringTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.templateViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.transactionDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.transactionListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.transactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(9).put(LazyClassKeyProvider.com_nyatetduwit_presentation_account_AccountViewModel, ((Provider) accountViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_budget_BudgetViewModel, ((Provider) budgetViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_category_CategoryViewModel, ((Provider) categoryViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel, ((Provider) recurringTransactionViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_template_TemplateViewModel, ((Provider) templateViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionDetailViewModel, ((Provider) transactionDetailViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionListViewModel, ((Provider) transactionListViewModelProvider)).put(LazyClassKeyProvider.com_nyatetduwit_presentation_transaction_TransactionViewModel, ((Provider) transactionViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_nyatetduwit_presentation_budget_BudgetViewModel = "com.nyatetduwit.presentation.budget.BudgetViewModel";

      static String com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel = "com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel";

      static String com_nyatetduwit_presentation_dashboard_DashboardViewModel = "com.nyatetduwit.presentation.dashboard.DashboardViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionDetailViewModel = "com.nyatetduwit.presentation.transaction.TransactionDetailViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionListViewModel = "com.nyatetduwit.presentation.transaction.TransactionListViewModel";

      static String com_nyatetduwit_presentation_template_TemplateViewModel = "com.nyatetduwit.presentation.template.TemplateViewModel";

      static String com_nyatetduwit_presentation_category_CategoryViewModel = "com.nyatetduwit.presentation.category.CategoryViewModel";

      static String com_nyatetduwit_presentation_account_AccountViewModel = "com.nyatetduwit.presentation.account.AccountViewModel";

      static String com_nyatetduwit_presentation_transaction_TransactionViewModel = "com.nyatetduwit.presentation.transaction.TransactionViewModel";

      @KeepFieldType
      BudgetViewModel com_nyatetduwit_presentation_budget_BudgetViewModel2;

      @KeepFieldType
      RecurringTransactionViewModel com_nyatetduwit_presentation_recurring_RecurringTransactionViewModel2;

      @KeepFieldType
      DashboardViewModel com_nyatetduwit_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      TransactionDetailViewModel com_nyatetduwit_presentation_transaction_TransactionDetailViewModel2;

      @KeepFieldType
      TransactionListViewModel com_nyatetduwit_presentation_transaction_TransactionListViewModel2;

      @KeepFieldType
      TemplateViewModel com_nyatetduwit_presentation_template_TemplateViewModel2;

      @KeepFieldType
      CategoryViewModel com_nyatetduwit_presentation_category_CategoryViewModel2;

      @KeepFieldType
      AccountViewModel com_nyatetduwit_presentation_account_AccountViewModel2;

      @KeepFieldType
      TransactionViewModel com_nyatetduwit_presentation_transaction_TransactionViewModel2;
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

          case 2: // com.nyatetduwit.presentation.category.CategoryViewModel 
          return (T) new CategoryViewModel(viewModelCImpl.getCategoriesUseCase(), viewModelCImpl.addCategoryUseCase(), viewModelCImpl.updateCategoryUseCase(), viewModelCImpl.deleteCategoryUseCase(), viewModelCImpl.seedDefaultCategoriesUseCase());

          case 3: // com.nyatetduwit.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(viewModelCImpl.getTotalBalanceUseCase(), viewModelCImpl.getSumByTypeAndDateRangeUseCase(), viewModelCImpl.getTopCategoriesByExpenseUseCase(), viewModelCImpl.getRecentTransactionsUseCase(), viewModelCImpl.getCategoriesUseCase(), singletonCImpl.bindSettingsRepositoryProvider.get());

          case 4: // com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel 
          return (T) new RecurringTransactionViewModel(viewModelCImpl.getRecurringTransactionsUseCase(), viewModelCImpl.getAllRecurringTransactionsUseCase(), singletonCImpl.bindRecurringTransactionRepositoryProvider.get(), singletonCImpl.bindTransactionRepositoryProvider.get(), viewModelCImpl.addRecurringTransactionUseCase(), viewModelCImpl.updateRecurringTransactionUseCase(), viewModelCImpl.deleteRecurringTransactionUseCase(), viewModelCImpl.deactivateRecurringTransactionUseCase(), viewModelCImpl.skipRecurringInstanceUseCase());

          case 5: // com.nyatetduwit.presentation.template.TemplateViewModel 
          return (T) new TemplateViewModel(viewModelCImpl.getTemplatesUseCase(), viewModelCImpl.deleteTemplateUseCase(), viewModelCImpl.toggleTemplatePinUseCase());

          case 6: // com.nyatetduwit.presentation.transaction.TransactionDetailViewModel 
          return (T) new TransactionDetailViewModel(viewModelCImpl.getTransactionByIdUseCase(), viewModelCImpl.getAccountByIdUseCase(), viewModelCImpl.getCategoryByIdUseCase(), viewModelCImpl.softDeleteTransactionUseCase(), viewModelCImpl.balanceUpdateService());

          case 7: // com.nyatetduwit.presentation.transaction.TransactionListViewModel 
          return (T) new TransactionListViewModel(viewModelCImpl.searchAndFilterTransactionsUseCase(), viewModelCImpl.softDeleteTransactionUseCase(), viewModelCImpl.restoreTransactionUseCase(), viewModelCImpl.getAccountsUseCase(), viewModelCImpl.getCategoriesUseCase(), viewModelCImpl.createTemplateFromTransactionUseCase());

          case 8: // com.nyatetduwit.presentation.transaction.TransactionViewModel 
          return (T) new TransactionViewModel(viewModelCImpl.getAccountsUseCase(), viewModelCImpl.getCategoriesByTypeUseCase(), viewModelCImpl.addTransactionUseCase(), viewModelCImpl.updateTransactionUseCase(), viewModelCImpl.getTransactionByIdUseCase(), viewModelCImpl.balanceUpdateService(), viewModelCImpl.getPinnedTemplatesUseCase(), viewModelCImpl.incrementTemplateUsageUseCase());

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

    private Provider<RecurringTransactionRepositoryImpl> recurringTransactionRepositoryImplProvider;

    private Provider<RecurringTransactionRepository> bindRecurringTransactionRepositoryProvider;

    private Provider<TransactionRepositoryImpl> transactionRepositoryImplProvider;

    private Provider<TransactionRepository> bindTransactionRepositoryProvider;

    private Provider<RecurringTransactionWorker_AssistedFactory> recurringTransactionWorker_AssistedFactoryProvider;

    private Provider<AccountRepositoryImpl> accountRepositoryImplProvider;

    private Provider<AccountRepository> bindAccountRepositoryProvider;

    private Provider<BudgetRepositoryImpl> budgetRepositoryImplProvider;

    private Provider<BudgetRepository> bindBudgetRepositoryProvider;

    private Provider<CategoryRepositoryImpl> categoryRepositoryImplProvider;

    private Provider<CategoryRepository> bindCategoryRepositoryProvider;

    private Provider<DataStore<Preferences>> provideDataStoreProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<SettingsRepository> bindSettingsRepositoryProvider;

    private Provider<TemplateRepositoryImpl> templateRepositoryImplProvider;

    private Provider<TemplateRepository> bindTemplateRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private RecurringTransactionDao recurringTransactionDao() {
      return DatabaseModule_ProvideRecurringTransactionDaoFactory.provideRecurringTransactionDao(provideDatabaseProvider.get());
    }

    private TransactionDao transactionDao() {
      return DatabaseModule_ProvideTransactionDaoFactory.provideTransactionDao(provideDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.nyatetduwit.core.worker.RecurringTransactionWorker", ((Provider) recurringTransactionWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private AccountDao accountDao() {
      return DatabaseModule_ProvideAccountDaoFactory.provideAccountDao(provideDatabaseProvider.get());
    }

    private BudgetDao budgetDao() {
      return DatabaseModule_ProvideBudgetDaoFactory.provideBudgetDao(provideDatabaseProvider.get());
    }

    private CategoryDao categoryDao() {
      return DatabaseModule_ProvideCategoryDaoFactory.provideCategoryDao(provideDatabaseProvider.get());
    }

    private TemplateDao templateDao() {
      return DatabaseModule_ProvideTemplateDaoFactory.provideTemplateDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<NyatetDuwitDatabase>(singletonCImpl, 2));
      this.recurringTransactionRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 1);
      this.bindRecurringTransactionRepositoryProvider = DoubleCheck.provider((Provider) recurringTransactionRepositoryImplProvider);
      this.transactionRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 3);
      this.bindTransactionRepositoryProvider = DoubleCheck.provider((Provider) transactionRepositoryImplProvider);
      this.recurringTransactionWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<RecurringTransactionWorker_AssistedFactory>(singletonCImpl, 0));
      this.accountRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 4);
      this.bindAccountRepositoryProvider = DoubleCheck.provider((Provider) accountRepositoryImplProvider);
      this.budgetRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 5);
      this.bindBudgetRepositoryProvider = DoubleCheck.provider((Provider) budgetRepositoryImplProvider);
      this.categoryRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 6);
      this.bindCategoryRepositoryProvider = DoubleCheck.provider((Provider) categoryRepositoryImplProvider);
      this.provideDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 8));
      this.settingsRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 7);
      this.bindSettingsRepositoryProvider = DoubleCheck.provider((Provider) settingsRepositoryImplProvider);
      this.templateRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 9);
      this.bindTemplateRepositoryProvider = DoubleCheck.provider((Provider) templateRepositoryImplProvider);
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
          case 0: // com.nyatetduwit.core.worker.RecurringTransactionWorker_AssistedFactory 
          return (T) new RecurringTransactionWorker_AssistedFactory() {
            @Override
            public RecurringTransactionWorker create(Context context,
                WorkerParameters workerParams) {
              return new RecurringTransactionWorker(context, workerParams, singletonCImpl.bindRecurringTransactionRepositoryProvider.get(), singletonCImpl.bindTransactionRepositoryProvider.get());
            }
          };

          case 1: // com.nyatetduwit.data.repository.RecurringTransactionRepositoryImpl 
          return (T) new RecurringTransactionRepositoryImpl(singletonCImpl.recurringTransactionDao());

          case 2: // com.nyatetduwit.data.local.NyatetDuwitDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.nyatetduwit.data.repository.TransactionRepositoryImpl 
          return (T) new TransactionRepositoryImpl(singletonCImpl.transactionDao());

          case 4: // com.nyatetduwit.data.repository.AccountRepositoryImpl 
          return (T) new AccountRepositoryImpl(singletonCImpl.accountDao(), singletonCImpl.transactionDao());

          case 5: // com.nyatetduwit.data.repository.BudgetRepositoryImpl 
          return (T) new BudgetRepositoryImpl(singletonCImpl.budgetDao(), singletonCImpl.transactionDao());

          case 6: // com.nyatetduwit.data.repository.CategoryRepositoryImpl 
          return (T) new CategoryRepositoryImpl(singletonCImpl.categoryDao());

          case 7: // com.nyatetduwit.data.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(singletonCImpl.provideDataStoreProvider.get());

          case 8: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DataStoreModule_ProvideDataStoreFactory.provideDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.nyatetduwit.data.repository.TemplateRepositoryImpl 
          return (T) new TemplateRepositoryImpl(singletonCImpl.templateDao(), singletonCImpl.transactionDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
