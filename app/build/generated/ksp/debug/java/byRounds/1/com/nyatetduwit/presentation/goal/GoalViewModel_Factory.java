package com.nyatetduwit.presentation.goal;

import com.nyatetduwit.domain.usecase.goal.AddGoalProgressUseCase;
import com.nyatetduwit.domain.usecase.goal.AddGoalUseCase;
import com.nyatetduwit.domain.usecase.goal.DeleteGoalUseCase;
import com.nyatetduwit.domain.usecase.goal.GetActiveGoalsUseCase;
import com.nyatetduwit.domain.usecase.goal.GetGoalByIdUseCase;
import com.nyatetduwit.domain.usecase.goal.UpdateGoalUseCase;
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
public final class GoalViewModel_Factory implements Factory<GoalViewModel> {
  private final Provider<GetActiveGoalsUseCase> getActiveGoalsUseCaseProvider;

  private final Provider<GetGoalByIdUseCase> getGoalByIdUseCaseProvider;

  private final Provider<AddGoalUseCase> addGoalUseCaseProvider;

  private final Provider<UpdateGoalUseCase> updateGoalUseCaseProvider;

  private final Provider<DeleteGoalUseCase> deleteGoalUseCaseProvider;

  private final Provider<AddGoalProgressUseCase> addGoalProgressUseCaseProvider;

  public GoalViewModel_Factory(Provider<GetActiveGoalsUseCase> getActiveGoalsUseCaseProvider,
      Provider<GetGoalByIdUseCase> getGoalByIdUseCaseProvider,
      Provider<AddGoalUseCase> addGoalUseCaseProvider,
      Provider<UpdateGoalUseCase> updateGoalUseCaseProvider,
      Provider<DeleteGoalUseCase> deleteGoalUseCaseProvider,
      Provider<AddGoalProgressUseCase> addGoalProgressUseCaseProvider) {
    this.getActiveGoalsUseCaseProvider = getActiveGoalsUseCaseProvider;
    this.getGoalByIdUseCaseProvider = getGoalByIdUseCaseProvider;
    this.addGoalUseCaseProvider = addGoalUseCaseProvider;
    this.updateGoalUseCaseProvider = updateGoalUseCaseProvider;
    this.deleteGoalUseCaseProvider = deleteGoalUseCaseProvider;
    this.addGoalProgressUseCaseProvider = addGoalProgressUseCaseProvider;
  }

  @Override
  public GoalViewModel get() {
    return newInstance(getActiveGoalsUseCaseProvider.get(), getGoalByIdUseCaseProvider.get(), addGoalUseCaseProvider.get(), updateGoalUseCaseProvider.get(), deleteGoalUseCaseProvider.get(), addGoalProgressUseCaseProvider.get());
  }

  public static GoalViewModel_Factory create(
      Provider<GetActiveGoalsUseCase> getActiveGoalsUseCaseProvider,
      Provider<GetGoalByIdUseCase> getGoalByIdUseCaseProvider,
      Provider<AddGoalUseCase> addGoalUseCaseProvider,
      Provider<UpdateGoalUseCase> updateGoalUseCaseProvider,
      Provider<DeleteGoalUseCase> deleteGoalUseCaseProvider,
      Provider<AddGoalProgressUseCase> addGoalProgressUseCaseProvider) {
    return new GoalViewModel_Factory(getActiveGoalsUseCaseProvider, getGoalByIdUseCaseProvider, addGoalUseCaseProvider, updateGoalUseCaseProvider, deleteGoalUseCaseProvider, addGoalProgressUseCaseProvider);
  }

  public static GoalViewModel newInstance(GetActiveGoalsUseCase getActiveGoalsUseCase,
      GetGoalByIdUseCase getGoalByIdUseCase, AddGoalUseCase addGoalUseCase,
      UpdateGoalUseCase updateGoalUseCase, DeleteGoalUseCase deleteGoalUseCase,
      AddGoalProgressUseCase addGoalProgressUseCase) {
    return new GoalViewModel(getActiveGoalsUseCase, getGoalByIdUseCase, addGoalUseCase, updateGoalUseCase, deleteGoalUseCase, addGoalProgressUseCase);
  }
}
