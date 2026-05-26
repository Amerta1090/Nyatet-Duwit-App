package com.nyatetduwit;

import androidx.hilt.work.HiltWrapper_WorkerFactoryModule;
import com.nyatetduwit.core.di.DataStoreModule;
import com.nyatetduwit.core.di.DatabaseModule;
import com.nyatetduwit.core.di.RepositoryModule;
import com.nyatetduwit.core.worker.AutoBackupWorker_HiltModule;
import com.nyatetduwit.core.worker.RecurringTransactionWorker_HiltModule;
import com.nyatetduwit.core.worker.ReminderWorker_HiltModule;
import com.nyatetduwit.core.worker.SyncWorker_HiltModule;
import com.nyatetduwit.presentation.account.AccountViewModel_HiltModules;
import com.nyatetduwit.presentation.budget.BudgetViewModel_HiltModules;
import com.nyatetduwit.presentation.cashflow.CashflowTrendViewModel_HiltModules;
import com.nyatetduwit.presentation.category.CategoryViewModel_HiltModules;
import com.nyatetduwit.presentation.dashboard.DashboardViewModel_HiltModules;
import com.nyatetduwit.presentation.debt.DebtViewModel_HiltModules;
import com.nyatetduwit.presentation.goal.GoalViewModel_HiltModules;
import com.nyatetduwit.presentation.investment.InvestmentViewModel_HiltModules;
import com.nyatetduwit.presentation.monthlysummary.MonthlySummaryViewModel_HiltModules;
import com.nyatetduwit.presentation.recurring.RecurringTransactionViewModel_HiltModules;
import com.nyatetduwit.presentation.remindersettings.ReminderSettingsViewModel_HiltModules;
import com.nyatetduwit.presentation.settings.SettingsViewModel_HiltModules;
import com.nyatetduwit.presentation.splitbill.SplitBillViewModel_HiltModules;
import com.nyatetduwit.presentation.template.TemplateViewModel_HiltModules;
import com.nyatetduwit.presentation.transaction.TransactionDetailViewModel_HiltModules;
import com.nyatetduwit.presentation.transaction.TransactionListViewModel_HiltModules;
import com.nyatetduwit.presentation.transaction.TransactionViewModel_HiltModules;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.components.ViewComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.components.ViewWithFragmentComponent;
import dagger.hilt.android.flags.FragmentGetContextFix;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_DefaultViewModelFactories_ActivityModule;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ViewModelModule;
import dagger.hilt.android.internal.managers.ActivityComponentManager;
import dagger.hilt.android.internal.managers.FragmentComponentManager;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_LifecycleModule;
import dagger.hilt.android.internal.managers.HiltWrapper_SavedStateHandleModule;
import dagger.hilt.android.internal.managers.ServiceComponentManager;
import dagger.hilt.android.internal.managers.ViewComponentManager;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.HiltWrapper_ActivityModule;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import dagger.hilt.android.scopes.ServiceScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.android.scopes.ViewScoped;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedComponent;
import dagger.hilt.migration.DisableInstallInCheck;
import javax.annotation.processing.Generated;
import javax.inject.Singleton;

@Generated("dagger.hilt.processor.internal.root.RootProcessor")
public final class NyatetDuwitApp_HiltComponents {
  private NyatetDuwitApp_HiltComponents() {
  }

  @Module(
      subcomponents = ServiceC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ServiceCBuilderModule {
    @Binds
    ServiceComponentBuilder bind(ServiceC.Builder builder);
  }

  @Module(
      subcomponents = ActivityRetainedC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityRetainedCBuilderModule {
    @Binds
    ActivityRetainedComponentBuilder bind(ActivityRetainedC.Builder builder);
  }

  @Module(
      subcomponents = ActivityC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityCBuilderModule {
    @Binds
    ActivityComponentBuilder bind(ActivityC.Builder builder);
  }

  @Module(
      subcomponents = ViewModelC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewModelCBuilderModule {
    @Binds
    ViewModelComponentBuilder bind(ViewModelC.Builder builder);
  }

  @Module(
      subcomponents = ViewC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewCBuilderModule {
    @Binds
    ViewComponentBuilder bind(ViewC.Builder builder);
  }

  @Module(
      subcomponents = FragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface FragmentCBuilderModule {
    @Binds
    FragmentComponentBuilder bind(FragmentC.Builder builder);
  }

  @Module(
      subcomponents = ViewWithFragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewWithFragmentCBuilderModule {
    @Binds
    ViewWithFragmentComponentBuilder bind(ViewWithFragmentC.Builder builder);
  }

  @Component(
      modules = {
          ApplicationContextModule.class,
          AutoBackupWorker_HiltModule.class,
          DataStoreModule.class,
          DatabaseModule.class,
          HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule.class,
          HiltWrapper_WorkerFactoryModule.class,
          ActivityRetainedCBuilderModule.class,
          ServiceCBuilderModule.class,
          RecurringTransactionWorker_HiltModule.class,
          ReminderWorker_HiltModule.class,
          RepositoryModule.class,
          SyncWorker_HiltModule.class
      }
  )
  @Singleton
  public abstract static class SingletonC implements NyatetDuwitApp_GeneratedInjector,
      FragmentGetContextFix.FragmentGetContextFixEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint,
      ServiceComponentManager.ServiceComponentBuilderEntryPoint,
      SingletonComponent,
      GeneratedComponent {
  }

  @Subcomponent
  @ServiceScoped
  public abstract static class ServiceC implements ServiceComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ServiceComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AccountViewModel_HiltModules.KeyModule.class,
          BudgetViewModel_HiltModules.KeyModule.class,
          CashflowTrendViewModel_HiltModules.KeyModule.class,
          CategoryViewModel_HiltModules.KeyModule.class,
          DashboardViewModel_HiltModules.KeyModule.class,
          DebtViewModel_HiltModules.KeyModule.class,
          GoalViewModel_HiltModules.KeyModule.class,
          HiltWrapper_ActivityRetainedComponentManager_LifecycleModule.class,
          HiltWrapper_SavedStateHandleModule.class,
          InvestmentViewModel_HiltModules.KeyModule.class,
          MonthlySummaryViewModel_HiltModules.KeyModule.class,
          ActivityCBuilderModule.class,
          ViewModelCBuilderModule.class,
          RecurringTransactionViewModel_HiltModules.KeyModule.class,
          ReminderSettingsViewModel_HiltModules.KeyModule.class,
          SettingsViewModel_HiltModules.KeyModule.class,
          SplitBillViewModel_HiltModules.KeyModule.class,
          TemplateViewModel_HiltModules.KeyModule.class,
          TransactionDetailViewModel_HiltModules.KeyModule.class,
          TransactionListViewModel_HiltModules.KeyModule.class,
          TransactionViewModel_HiltModules.KeyModule.class
      }
  )
  @ActivityRetainedScoped
  public abstract static class ActivityRetainedC implements ActivityRetainedComponent,
      ActivityComponentManager.ActivityComponentBuilderEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityRetainedComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          HiltWrapper_ActivityModule.class,
          HiltWrapper_DefaultViewModelFactories_ActivityModule.class,
          FragmentCBuilderModule.class,
          ViewCBuilderModule.class
      }
  )
  @ActivityScoped
  public abstract static class ActivityC implements MainActivity_GeneratedInjector,
      ActivityComponent,
      DefaultViewModelFactories.ActivityEntryPoint,
      HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint,
      FragmentComponentManager.FragmentComponentBuilderEntryPoint,
      ViewComponentManager.ViewComponentBuilderEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AccountViewModel_HiltModules.BindsModule.class,
          BudgetViewModel_HiltModules.BindsModule.class,
          CashflowTrendViewModel_HiltModules.BindsModule.class,
          CategoryViewModel_HiltModules.BindsModule.class,
          DashboardViewModel_HiltModules.BindsModule.class,
          DebtViewModel_HiltModules.BindsModule.class,
          GoalViewModel_HiltModules.BindsModule.class,
          HiltWrapper_HiltViewModelFactory_ViewModelModule.class,
          InvestmentViewModel_HiltModules.BindsModule.class,
          MonthlySummaryViewModel_HiltModules.BindsModule.class,
          RecurringTransactionViewModel_HiltModules.BindsModule.class,
          ReminderSettingsViewModel_HiltModules.BindsModule.class,
          SettingsViewModel_HiltModules.BindsModule.class,
          SplitBillViewModel_HiltModules.BindsModule.class,
          TemplateViewModel_HiltModules.BindsModule.class,
          TransactionDetailViewModel_HiltModules.BindsModule.class,
          TransactionListViewModel_HiltModules.BindsModule.class,
          TransactionViewModel_HiltModules.BindsModule.class
      }
  )
  @ViewModelScoped
  public abstract static class ViewModelC implements ViewModelComponent,
      HiltViewModelFactory.ViewModelFactoriesEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewModelComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewC implements ViewComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewComponentBuilder {
    }
  }

  @Subcomponent(
      modules = ViewWithFragmentCBuilderModule.class
  )
  @FragmentScoped
  public abstract static class FragmentC implements FragmentComponent,
      DefaultViewModelFactories.FragmentEntryPoint,
      ViewComponentManager.ViewWithFragmentComponentBuilderEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends FragmentComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewWithFragmentC implements ViewWithFragmentComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewWithFragmentComponentBuilder {
    }
  }
}
