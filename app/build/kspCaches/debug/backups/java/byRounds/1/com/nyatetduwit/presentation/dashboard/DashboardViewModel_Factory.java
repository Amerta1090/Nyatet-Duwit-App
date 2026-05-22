package com.nyatetduwit.presentation.dashboard;

import com.nyatetduwit.domain.repository.SettingsRepository;
import com.nyatetduwit.domain.usecase.account.GetTotalBalanceUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetRecentTransactionsUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetSumByTypeAndDateRangeUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTopCategoriesByExpenseUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetTotalBalanceUseCase> getTotalBalanceUseCaseProvider;

  private final Provider<GetSumByTypeAndDateRangeUseCase> getSumByTypeAndDateRangeUseCaseProvider;

  private final Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider;

  private final Provider<GetRecentTransactionsUseCase> getRecentTransactionsUseCaseProvider;

  private final Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public DashboardViewModel_Factory(Provider<GetTotalBalanceUseCase> getTotalBalanceUseCaseProvider,
      Provider<GetSumByTypeAndDateRangeUseCase> getSumByTypeAndDateRangeUseCaseProvider,
      Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider,
      Provider<GetRecentTransactionsUseCase> getRecentTransactionsUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.getTotalBalanceUseCaseProvider = getTotalBalanceUseCaseProvider;
    this.getSumByTypeAndDateRangeUseCaseProvider = getSumByTypeAndDateRangeUseCaseProvider;
    this.getTopCategoriesByExpenseUseCaseProvider = getTopCategoriesByExpenseUseCaseProvider;
    this.getRecentTransactionsUseCaseProvider = getRecentTransactionsUseCaseProvider;
    this.getCategoriesUseCaseProvider = getCategoriesUseCaseProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getTotalBalanceUseCaseProvider.get(), getSumByTypeAndDateRangeUseCaseProvider.get(), getTopCategoriesByExpenseUseCaseProvider.get(), getRecentTransactionsUseCaseProvider.get(), getCategoriesUseCaseProvider.get(), settingsRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetTotalBalanceUseCase> getTotalBalanceUseCaseProvider,
      Provider<GetSumByTypeAndDateRangeUseCase> getSumByTypeAndDateRangeUseCaseProvider,
      Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider,
      Provider<GetRecentTransactionsUseCase> getRecentTransactionsUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new DashboardViewModel_Factory(getTotalBalanceUseCaseProvider, getSumByTypeAndDateRangeUseCaseProvider, getTopCategoriesByExpenseUseCaseProvider, getRecentTransactionsUseCaseProvider, getCategoriesUseCaseProvider, settingsRepositoryProvider);
  }

  public static DashboardViewModel newInstance(GetTotalBalanceUseCase getTotalBalanceUseCase,
      GetSumByTypeAndDateRangeUseCase getSumByTypeAndDateRangeUseCase,
      GetTopCategoriesByExpenseUseCase getTopCategoriesByExpenseUseCase,
      GetRecentTransactionsUseCase getRecentTransactionsUseCase,
      GetCategoriesUseCase getCategoriesUseCase, SettingsRepository settingsRepository) {
    return new DashboardViewModel(getTotalBalanceUseCase, getSumByTypeAndDateRangeUseCase, getTopCategoriesByExpenseUseCase, getRecentTransactionsUseCase, getCategoriesUseCase, settingsRepository);
  }
}
