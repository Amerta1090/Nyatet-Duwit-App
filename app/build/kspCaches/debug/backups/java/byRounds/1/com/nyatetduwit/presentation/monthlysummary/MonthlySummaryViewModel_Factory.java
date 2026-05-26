package com.nyatetduwit.presentation.monthlysummary;

import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetActiveDaysCountUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetBiggestExpenseUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetDailyExpenseTrendUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetMonthComparisonUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetSumByTypeAndDateRangeUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTopCategoriesByExpenseUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTransactionCountUseCase;
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
public final class MonthlySummaryViewModel_Factory implements Factory<MonthlySummaryViewModel> {
  private final Provider<GetSumByTypeAndDateRangeUseCase> getSumByTypeAndDateRangeUseCaseProvider;

  private final Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider;

  private final Provider<GetDailyExpenseTrendUseCase> getDailyExpenseTrendUseCaseProvider;

  private final Provider<GetBiggestExpenseUseCase> getBiggestExpenseUseCaseProvider;

  private final Provider<GetMonthComparisonUseCase> getMonthComparisonUseCaseProvider;

  private final Provider<GetActiveDaysCountUseCase> getActiveDaysCountUseCaseProvider;

  private final Provider<GetTransactionCountUseCase> getTransactionCountUseCaseProvider;

  private final Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider;

  public MonthlySummaryViewModel_Factory(
      Provider<GetSumByTypeAndDateRangeUseCase> getSumByTypeAndDateRangeUseCaseProvider,
      Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider,
      Provider<GetDailyExpenseTrendUseCase> getDailyExpenseTrendUseCaseProvider,
      Provider<GetBiggestExpenseUseCase> getBiggestExpenseUseCaseProvider,
      Provider<GetMonthComparisonUseCase> getMonthComparisonUseCaseProvider,
      Provider<GetActiveDaysCountUseCase> getActiveDaysCountUseCaseProvider,
      Provider<GetTransactionCountUseCase> getTransactionCountUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider) {
    this.getSumByTypeAndDateRangeUseCaseProvider = getSumByTypeAndDateRangeUseCaseProvider;
    this.getTopCategoriesByExpenseUseCaseProvider = getTopCategoriesByExpenseUseCaseProvider;
    this.getDailyExpenseTrendUseCaseProvider = getDailyExpenseTrendUseCaseProvider;
    this.getBiggestExpenseUseCaseProvider = getBiggestExpenseUseCaseProvider;
    this.getMonthComparisonUseCaseProvider = getMonthComparisonUseCaseProvider;
    this.getActiveDaysCountUseCaseProvider = getActiveDaysCountUseCaseProvider;
    this.getTransactionCountUseCaseProvider = getTransactionCountUseCaseProvider;
    this.getCategoriesUseCaseProvider = getCategoriesUseCaseProvider;
  }

  @Override
  public MonthlySummaryViewModel get() {
    return newInstance(getSumByTypeAndDateRangeUseCaseProvider.get(), getTopCategoriesByExpenseUseCaseProvider.get(), getDailyExpenseTrendUseCaseProvider.get(), getBiggestExpenseUseCaseProvider.get(), getMonthComparisonUseCaseProvider.get(), getActiveDaysCountUseCaseProvider.get(), getTransactionCountUseCaseProvider.get(), getCategoriesUseCaseProvider.get());
  }

  public static MonthlySummaryViewModel_Factory create(
      Provider<GetSumByTypeAndDateRangeUseCase> getSumByTypeAndDateRangeUseCaseProvider,
      Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider,
      Provider<GetDailyExpenseTrendUseCase> getDailyExpenseTrendUseCaseProvider,
      Provider<GetBiggestExpenseUseCase> getBiggestExpenseUseCaseProvider,
      Provider<GetMonthComparisonUseCase> getMonthComparisonUseCaseProvider,
      Provider<GetActiveDaysCountUseCase> getActiveDaysCountUseCaseProvider,
      Provider<GetTransactionCountUseCase> getTransactionCountUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider) {
    return new MonthlySummaryViewModel_Factory(getSumByTypeAndDateRangeUseCaseProvider, getTopCategoriesByExpenseUseCaseProvider, getDailyExpenseTrendUseCaseProvider, getBiggestExpenseUseCaseProvider, getMonthComparisonUseCaseProvider, getActiveDaysCountUseCaseProvider, getTransactionCountUseCaseProvider, getCategoriesUseCaseProvider);
  }

  public static MonthlySummaryViewModel newInstance(
      GetSumByTypeAndDateRangeUseCase getSumByTypeAndDateRangeUseCase,
      GetTopCategoriesByExpenseUseCase getTopCategoriesByExpenseUseCase,
      GetDailyExpenseTrendUseCase getDailyExpenseTrendUseCase,
      GetBiggestExpenseUseCase getBiggestExpenseUseCase,
      GetMonthComparisonUseCase getMonthComparisonUseCase,
      GetActiveDaysCountUseCase getActiveDaysCountUseCase,
      GetTransactionCountUseCase getTransactionCountUseCase,
      GetCategoriesUseCase getCategoriesUseCase) {
    return new MonthlySummaryViewModel(getSumByTypeAndDateRangeUseCase, getTopCategoriesByExpenseUseCase, getDailyExpenseTrendUseCase, getBiggestExpenseUseCase, getMonthComparisonUseCase, getActiveDaysCountUseCase, getTransactionCountUseCase, getCategoriesUseCase);
  }
}
